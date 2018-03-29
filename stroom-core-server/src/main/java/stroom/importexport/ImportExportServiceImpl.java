/*
 * Copyright 2016 Crown Copyright
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package stroom.importexport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import stroom.entity.shared.DocRefs;
import stroom.entity.util.EntityServiceExceptionUtil;
import stroom.importexport.shared.ImportState;
import stroom.importexport.shared.ImportState.ImportMode;
import stroom.util.io.FileUtil;
import stroom.util.shared.Message;
import stroom.util.shared.SharedList;
import stroom.util.zip.ZipUtil;

import javax.inject.Inject;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * Service to export standing data in and out from Stroom. It uses a ZIP format to
 * hold a HSQLDB database.
 */
public class ImportExportServiceImpl implements ImportExportService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ImportExportServiceImpl.class);

    private final ImportExportSerializer importExportSerializer;

    @Inject
    public ImportExportServiceImpl(final ImportExportSerializer importExportSerializer) {
        this.importExportSerializer = importExportSerializer;
    }

    @Override
    public SharedList<ImportState> createImportConfirmationList(final Path data) {
        final SharedList<ImportState> confirmList = new SharedList<>();
        doImport(data, confirmList, ImportMode.CREATE_CONFIRMATION);
        return confirmList;
    }

    /**
     * Import API.
     */
    @Override
    public void performImportWithConfirmation(final Path data, final List<ImportState> confirmList) {
        doImport(data, confirmList, ImportMode.ACTION_CONFIRMATION);
    }

    @Override
    public void performImportWithoutConfirmation(final Path data) {
        doImport(data, null, ImportMode.IGNORE_CONFIRMATION);
    }

    private void doImport(final Path zipFile, final List<ImportState> confirmList,
                          final ImportMode importMode) {
        final Path explodeDir = ZipUtil.workingZipDir(zipFile);

        try {
            Files.createDirectories(explodeDir);

            // Unzip the zip file.
            ZipUtil.unzip(zipFile, explodeDir);

            importExportSerializer.read(explodeDir, confirmList, importMode);
        } catch (final IOException | RuntimeException e) {
            throw EntityServiceExceptionUtil.create(e);
        } finally {
            FileUtil.deleteDir(explodeDir);
        }
    }

    /**
     * Export the selected folder data.
     */
    @Override
    public void exportConfig(final DocRefs docRefs, final Path zipFile,
                             final List<Message> messageList) {
        final Path explodeDir = ZipUtil.workingZipDir(zipFile);

        try {
            Files.createDirectories(explodeDir);

            // Serialize the config in a human readable tree structure.
            importExportSerializer.write(explodeDir, docRefs, false, messageList);

            // Now zip the dir.
            ZipUtil.zip(zipFile, explodeDir);

        } catch (final IOException e) {
            throw EntityServiceExceptionUtil.create(e);
        } finally {
            FileUtil.deleteDir(explodeDir);
        }
    }
}
