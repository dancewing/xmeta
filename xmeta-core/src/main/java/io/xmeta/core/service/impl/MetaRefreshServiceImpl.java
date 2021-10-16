package io.xmeta.core.service.impl;

import com.zaxxer.hikari.util.UtilityElf;
import io.xmeta.core.service.MetaCacheLoaderService;
import io.xmeta.core.service.MetaDatabaseLoaderService;
import io.xmeta.core.service.MetaRefreshService;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;

@Slf4j
public class MetaRefreshServiceImpl implements MetaRefreshService {

    private static final String POOL_NAME = "Meta refresh - ";

    private final MetaDatabaseLoaderService metaAdminService;
    private final MetaCacheLoaderService metaCacheLoaderService;

    private final ScheduledExecutorService houseKeepingExecutorService;
    private ScheduledFuture<?> houseKeeperTask;

    public MetaRefreshServiceImpl(MetaDatabaseLoaderService metaLoaderService, MetaCacheLoaderService metaCacheLoaderService) {
        this.metaAdminService = metaLoaderService;
        this.metaCacheLoaderService = metaCacheLoaderService;
        this.houseKeepingExecutorService = initializeHouseKeepingExecutorService();
        this.houseKeeperTask = houseKeepingExecutorService.scheduleWithFixedDelay(new HouseKeeper(), 100L, SECONDS.toMillis(10), MILLISECONDS);
    }

    private ScheduledExecutorService initializeHouseKeepingExecutorService() {
        final ThreadFactory threadFactory = new UtilityElf.DefaultThreadFactory(POOL_NAME + " housekeeper", true);
        final ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1, threadFactory, new ThreadPoolExecutor.DiscardPolicy());
        executor.setExecuteExistingDelayedTasksAfterShutdownPolicy(false);
        executor.setRemoveOnCancelPolicy(true);
        return executor;
    }

    private final class HouseKeeper implements Runnable {
        private volatile long previous = 0L;

        @Override
        public void run() {
            try {
                long currentTime = metaAdminService.getLastSyncTime();
                if (previous == 0L) {
                    log.info("Load entities to cache");
                    metaCacheLoaderService.save(metaAdminService.load());
                    previous = currentTime;
                } else if (currentTime > previous) {
                    log.warn("Need to refresh meta");
                    metaCacheLoaderService.save(metaAdminService.load());
                    previous = currentTime;
                }
            } catch (Exception e) {
                log.error("Unexpected exception in housekeeping task", e);
            }
        }
    }

    @Override
    public void close() throws Exception {
        if (houseKeeperTask != null) {
            houseKeeperTask.cancel(false);
            houseKeeperTask = null;
        }

        if (houseKeepingExecutorService != null) {
            houseKeepingExecutorService.shutdown();
        }
    }
}
