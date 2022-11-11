package org.gymanager.service.specification;

import com.itextpdf.text.DocumentException;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public interface ExportService {
    void export(
            OutputStream outputStream,
            List<List<String>> tableData,
            String title,
            List<String> headers
    ) throws IOException, DocumentException;
}
