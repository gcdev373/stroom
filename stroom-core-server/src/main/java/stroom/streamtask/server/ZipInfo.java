package stroom.streamtask.server;

import stroom.util.date.DateUtil;
import stroom.util.io.FileUtil;
import stroom.util.shared.ModelStringUtil;

import java.nio.file.Path;

public class ZipInfo {
    private final Path path;
    private final String feedName;
    private final Long uncompressedSize;
    private final Long compressedSize;
    private final Long lastModified;
    private final Integer zipEntryCount;

    ZipInfo(final Path path, final String feedName, final Long uncompressedSize, final Long compressedSize, final Long lastModified, final Integer zipEntryCount) {
        this.path = path;
        this.feedName = feedName;
        this.uncompressedSize = uncompressedSize;
        this.compressedSize = compressedSize;
        this.lastModified = lastModified;
        this.zipEntryCount = zipEntryCount;
    }

    public Path getPath() {
        return path;
    }

    public String getFeedName() {
        return feedName;
    }

    public Long getUncompressedSize() {
        return uncompressedSize;
    }

    public Long getCompressedSize() {
        return compressedSize;
    }

    public Long getLastModified() {
        return lastModified;
    }

    public Integer getZipEntryCount() {
        return zipEntryCount;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(FileUtil.getCanonicalPath(path));
        if (feedName != null) {
            sb.append("\n\tfeedName=");
            sb.append(feedName);
        }
        if (uncompressedSize != null) {
            sb.append("\n\tuncompressedSize=");
            sb.append(ModelStringUtil.formatIECByteSizeString(uncompressedSize));
        }
        if (compressedSize != null) {
            sb.append("\n\tcompressedSize=");
            sb.append(ModelStringUtil.formatIECByteSizeString(compressedSize));
        }
        if (lastModified != null) {
            sb.append("\n\tlastModified=");
            sb.append(DateUtil.createNormalDateTimeString(lastModified));
        }
        if (zipEntryCount != null) {
            sb.append("\n\tzipEntryCount=");
            sb.append(zipEntryCount);
        }
        return sb.toString();
    }
}