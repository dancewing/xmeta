package io.xmeta.admin.model;


public interface IBlock {

    @javax.validation.constraints.NotNull
    String getId();

    @javax.validation.constraints.NotNull
    java.time.ZonedDateTime getCreatedAt();

    @javax.validation.constraints.NotNull
    java.time.ZonedDateTime getUpdatedAt();

    Block getParentBlock();

    @javax.validation.constraints.NotNull
    String getDisplayName();

    @javax.validation.constraints.NotNull
    String getDescription();

    @javax.validation.constraints.NotNull
    EnumBlockType getBlockType();

    double getVersionNumber();

    @javax.validation.constraints.NotNull
    java.util.List<BlockInputOutput> getInputParameters();

    @javax.validation.constraints.NotNull
    java.util.List<BlockInputOutput> getOutputParameters();

    String getLockedByUserId();

    java.time.ZonedDateTime getLockedAt();

}
