package io.xmeta.core.service.impl;

import com.zaxxer.hikari.util.UtilityElf;
import io.xmeta.core.service.MetaLoaderService;
import io.xmeta.core.service.MetaRefreshService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.*;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;

@Slf4j
public class MetaRefreshServiceImpl implements MetaRefreshService {

    private static final String POOL_NAME = "Meta refresh - ";

    private final MetaLoaderService metaLoaderService;

    private final ScheduledExecutorService houseKeepingExecutorService;
    private ScheduledFuture<?> houseKeeperTask;

    public MetaRefreshServiceImpl(MetaLoaderService metaLoaderService) {
        this.metaLoaderService = metaLoaderService;
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
                long currentTime = metaLoaderService.getLastSyncTime();
                if (previous == 0L) {
                    previous = currentTime;
                } else if (currentTime > previous) {
                    log.warn("need to refresh meta");

                    //previous = currentTime;
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
