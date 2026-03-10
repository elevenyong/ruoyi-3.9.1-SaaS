package com.ruoyi.hh.hhhz.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * version对象 version
 *
 * @author hhhz
 * @date 2022-08-22
 */
public class Version extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    private Long id;

    /**
     *
     */
    @Excel(name = "")
    private String versionName;

    /**
     *
     */
    @Excel(name = "")
    private Integer forceUpdateGame;

    private Integer currentVersion;
    /**
     *
     */
    @Excel(name = "")
    private Long internalGameVersion;

    /**
     *
     */
    @Excel(name = "")
    private Long internalResourceVersion;

    /**
     *
     */
    @Excel(name = "")
    private Long versionListLength;

    /**
     *
     */
    @Excel(name = "")
    private Long versionListHashCode;

    /**
     *
     */
    @Excel(name = "")
    private Long versionListZipLength;

    /**
     *
     */
    @Excel(name = "")
    private Long versionListZipHashCode;

    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String updatePrefixUri;

    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String latestGameVersion;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public Integer getCurrentVersion() {
        return currentVersion;
    }

    public void setCurrentVersion(Integer currentVersion) {
        this.currentVersion = currentVersion;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setForceUpdateGame(Integer forceUpdateGame) {
        this.forceUpdateGame = forceUpdateGame;
    }

    public Integer getForceUpdateGame() {
        return forceUpdateGame;
    }

    public void setInternalGameVersion(Long internalGameVersion) {
        this.internalGameVersion = internalGameVersion;
    }

    public Long getInternalGameVersion() {
        return internalGameVersion;
    }

    public void setInternalResourceVersion(Long internalResourceVersion) {
        this.internalResourceVersion = internalResourceVersion;
    }

    public Long getInternalResourceVersion() {
        return internalResourceVersion;
    }

    public void setVersionListLength(Long versionListLength) {
        this.versionListLength = versionListLength;
    }

    public Long getVersionListLength() {
        return versionListLength;
    }

    public void setVersionListHashCode(Long versionListHashCode) {
        this.versionListHashCode = versionListHashCode;
    }

    public Long getVersionListHashCode() {
        return versionListHashCode;
    }

    public void setVersionListZipLength(Long versionListZipLength) {
        this.versionListZipLength = versionListZipLength;
    }

    public Long getVersionListZipLength() {
        return versionListZipLength;
    }

    public void setVersionListZipHashCode(Long versionListZipHashCode) {
        this.versionListZipHashCode = versionListZipHashCode;
    }

    public Long getVersionListZipHashCode() {
        return versionListZipHashCode;
    }

    public void setUpdatePrefixUri(String updatePrefixUri) {
        this.updatePrefixUri = updatePrefixUri;
    }

    public String getUpdatePrefixUri() {
        return updatePrefixUri;
    }

    public void setLatestGameVersion(String latestGameVersion) {
        this.latestGameVersion = latestGameVersion;
    }

    public String getLatestGameVersion() {
        return latestGameVersion;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("versionName", getVersionName())
                .append("forceUpdateGame", getForceUpdateGame())
                .append("currentVersion", getCurrentVersion())
                .append("internalGameVersion", getInternalGameVersion())
                .append("internalResourceVersion", getInternalResourceVersion())
                .append("versionListLength", getVersionListLength())
                .append("versionListHashCode", getVersionListHashCode())
                .append("versionListZipLength", getVersionListZipLength())
                .append("versionListZipHashCode", getVersionListZipHashCode())
                .append("updatePrefixUri", getUpdatePrefixUri())
                .append("latestGameVersion", getLatestGameVersion())
                .toString();
    }
}
