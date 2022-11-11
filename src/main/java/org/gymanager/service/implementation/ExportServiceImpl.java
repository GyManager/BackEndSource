package org.gymanager.service.implementation;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.gymanager.service.specification.ExportService;
import org.springframework.stereotype.Service;

import java.io.OutputStream;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class ExportServiceImpl implements ExportService {

    @Override
    public void export(OutputStream outputStream, List<List<String>> tableData, String title, List<String> headers) throws DocumentException {
        var document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, outputStream);

        document.open();
        document.add(createTitle(title));

        var table = createTable(headers.size());
        writeTableHeader(table, headers);
        writeTableData(table, tableData);

        document.add(table);
        document.close();
    }

    private Paragraph createTitle(String title) {
        var font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setSize(24);
        font.setColor(BaseColor.BLACK);

        var p = new Paragraph(title, font);
        p.setAlignment(Paragraph.ALIGN_CENTER);

        return p;
    }

    private PdfPTable createTable(Integer colNumber) throws DocumentException {
        var table = new PdfPTable(colNumber);
        table.setWidthPercentage(100f);
        table.setWidths(new float[] {3.0f, 3.0f, 4.5f}); // TODO should be configurable
        table.setSpacingBefore(15);
        return table;
    }

    private void writeTableHeader(PdfPTable table, List<String> headers) {
        var cell = createCell(8, new BaseColor(54, 162, 235, 204));

        var font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setColor(BaseColor.WHITE);

        headers.forEach(header -> {
            cell.setPhrase(new Phrase(header, font));
            table.addCell(cell);
        });
    }

    private void writeTableData(PdfPTable table, List<List<String>> data) {
        var cell = createCell(5);
        var font = FontFactory.getFont(FontFactory.HELVETICA);

        data.forEach(row -> row.forEach(value -> {
            cell.setPhrase(new Phrase(value, font));
            table.addCell(cell);
        }));
    }

    private PdfPCell createCell(Integer padding){
        return createCell(padding, null);
    }

    private PdfPCell createCell(Integer padding, BaseColor backgroundColor){
        var cell = new PdfPCell();
        if(Objects.nonNull(backgroundColor)){
            cell.setBackgroundColor(backgroundColor);
        }
        cell.setPadding(padding);

        return cell;
    }

}
