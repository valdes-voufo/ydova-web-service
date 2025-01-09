package com.ydova.cv.service.util;

import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import com.itextpdf.layout.properties.VerticalAlignment;
import com.ydova.cv.CVGenerationException;
import com.ydova.cv.CVGenerationModule;
import com.ydova.ahub.dto.AHubClientDto;
import com.ydova.ahub.dto.LanguageLevel;
import com.ydova.ahub.dto.TimeLineEntryDto;

import java.io.*;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;


public class DefaultCVGenerator implements CVGenerationStrategy {

    private final Locale locale = new Locale("de");
    private final ResourceBundle bundle = ResourceBundle.getBundle("com/ydova/cv/cv", locale);
    private static final DeviceRgb grayColor = new DeviceRgb(220, 220, 220);

    private static final float TIMELINE_X_OFFSET = 350;
    private static final float RIGHT_TEXT_X_OFFSET = 230;
    private static final float TEXT_Y_OFFSET = 690;
    private static final float GAP_BETWEEN_ENTRIES = 80;

    @Override
    public File generate(AHubClientDto dto) throws CVGenerationException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();


        PdfWriter writer;
        writer = new PdfWriter(baos);

        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);
        PdfCanvas canvas = new PdfCanvas(pdf.addNewPage());


        document.setMargins(0, 0, 0, 0);
        Table layoutTable = new Table(UnitValue.createPercentArray(new float[]{1, 4}));
        layoutTable.setWidth(UnitValue.createPercentValue(100));


        //make a left section
        layoutTable.addCell(mkLeftSection(canvas, document, dto));


        //make the right section
        mkRightSection(canvas, document, dto);

        // add layout table
        document.add(layoutTable);


        // add Language information
        Paragraph title = new Paragraph(bundle.getString("language.label")).setBold().setFontSize(14).setTextAlignment(TextAlignment.LEFT);
        document.showTextAligned(title, 10, 120, TextAlignment.LEFT);

        if (dto.getLanguageLevels() != null) {
            for (int i = 0; i < dto.getLanguageLevels().size(); i++) {
                LanguageLevel language = dto.getLanguageLevels().get(i);
                addLanguageBar(canvas, document, language, 10 + CVGenerationModule.GAP_BETWEEN_LANGUAGES * i);
            }
        }


        document.close();


        // Step 5: Write ByteArrayOutputStream to file
        File pdfFile = new File("Example.pdf");
        try (FileOutputStream fos = new FileOutputStream(pdfFile)) {
            baos.writeTo(fos);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return pdfFile; // Return the file
    }


    public static void addLanguageBar(PdfCanvas canvas, Document document, LanguageLevel lang, float y) {

        float x = 10;  // Starting x-coordinate
        float barWidth = 150; // Total width of the bar
        float barHeight = 10; // Height of the bar

        // Add the language label
        Paragraph label = new Paragraph(lang.getLanguage() + "(" + lang.getDescription() + ")").setFontSize(12).setTextAlignment(TextAlignment.LEFT);
        document.showTextAligned(label, x, y + 15, TextAlignment.LEFT);


        canvas.setFillColor(ColorConstants.LIGHT_GRAY);
        canvas.rectangle(x, y, barWidth, barHeight);
        canvas.fill();

        // Draw the proficiency bar
        canvas.setFillColor(ColorConstants.BLACK);
        canvas.rectangle(x, y, barWidth * lang.getLevel(), barHeight);
        canvas.fill();

    }


    public void mkRightSection(PdfCanvas canvas, Document document, AHubClientDto dto) {

        // name + profession
        Cell nameCell = new Cell().setFixedPosition(RIGHT_TEXT_X_OFFSET, 770, UnitValue.createPercentValue(100));
        Paragraph name = new Paragraph(dto.getFullName()).setFontSize(24).setBold();
        Paragraph profession = new Paragraph(dto.getStatus()).setFontSize(12);


        //experience label + line
        String experienceLabel = bundle.getString("experience.label");
        Paragraph experience = new Paragraph(experienceLabel).setFontColor(grayColor).setFontSize(15);
        canvas.setLineWidth(1).setStrokeColor(grayColor);
        canvas.moveTo(335, TEXT_Y_OFFSET + 47).lineTo(550, TEXT_Y_OFFSET + 47).stroke();
        document.showTextAligned(experience, RIGHT_TEXT_X_OFFSET, TEXT_Y_OFFSET + 40, TextAlignment.LEFT);
        nameCell.add(name);
        nameCell.add(profession);
        document.add(nameCell);

        // Add Experiences as timeline
        if (dto.getExperience() != null) {
            addTimeLine(document, canvas, dto.getExperience(), TEXT_Y_OFFSET - 20);
        }


        //ausbildung +line
        int expCount;
        if (dto.getExperience() != null) {
            expCount = dto.getExperience().size();
        } else {
            expCount = 0;
        }
        String educationLabel = bundle.getString("education.label");
        Paragraph education = new Paragraph(educationLabel).setFontColor(grayColor).setFontSize(15);
        document.showTextAligned(education, 230, TEXT_Y_OFFSET - expCount * GAP_BETWEEN_ENTRIES, TextAlignment.LEFT);
        canvas.setLineWidth(1).setStrokeColor(grayColor);

        float ya = TEXT_Y_OFFSET - expCount * GAP_BETWEEN_ENTRIES + 7;
        canvas.moveTo(307, ya).lineTo(550, ya).stroke();

        // Add Experiences as timeline
        if (dto.getEducation() != null) {
            addTimeLine(document, canvas, dto.getEducation(), ya - GAP_BETWEEN_ENTRIES);
        }


    }


    private void addTimeLine(Document document, PdfCanvas canvas, List<TimeLineEntryDto> timeLineEntries, float y) {
        if (timeLineEntries == null || timeLineEntries.isEmpty()) {
            return;
        }


        //vertical line
        canvas.setLineWidth(1).setStrokeColor(ColorConstants.GRAY);
        canvas.moveTo(TIMELINE_X_OFFSET, y + 30).lineTo(TIMELINE_X_OFFSET, y + 30 - GAP_BETWEEN_ENTRIES * (timeLineEntries.size() - 1)).stroke();

        for (int i = 0; i < timeLineEntries.size(); i++) {
            canvas.circle(TIMELINE_X_OFFSET, y + 30 - GAP_BETWEEN_ENTRIES * i, 5).fill();
        }


        for (int i = 0; i < timeLineEntries.size(); i++) {
            // Left column: Date and Institution
            TimeLineEntryDto entry = timeLineEntries.get(i);
            Cell leftCell = new Cell().setFontSize(8).setFixedPosition(RIGHT_TEXT_X_OFFSET, y - GAP_BETWEEN_ENTRIES * i, 80);

            leftCell.add(new Paragraph(entry.getInstitution()).setFontSize(10));
            leftCell.add(new Paragraph(entry.getPeriod()));
            leftCell.add(new Paragraph(entry.getPlace()));

            document.add(leftCell);


            Cell rightCell = new Cell().setFixedPosition(RIGHT_TEXT_X_OFFSET + 30 + 100, y - GAP_BETWEEN_ENTRIES * i - 14, 50);
            rightCell.add(new Paragraph(entry.getTask()).setFontSize(12));
            entry.getTaskDescription().forEach(item -> rightCell.add(new Paragraph(item).setPaddingLeft(10).setFontSize(8)));

            document.add(rightCell);

        }


    }


    public Cell mkLeftSection(PdfCanvas canvas, Document document, AHubClientDto dto) throws CVGenerationException {

        Cell leftSection = new Cell();


        String imagePath = "src/main/resources/com/ydova/cv/photo.jpeg"; // todo take image from dto

        Image img = loadImage(imagePath);

        img.setWidth(UnitValue.createPercentValue(100));
        img.setHeight(UnitValue.createPercentValue(100));
        img.setTextAlignment(TextAlignment.CENTER);

        leftSection.add(img);


        Cell leftCenter = new Cell();
        leftCenter.setPadding(10);


        //adding address
        Cell addressLabel = new Cell().setBorder(Border.NO_BORDER);
        addressLabel.add(new Paragraph(dto.geStreetAndCity()));
        addressLabel.add(new Paragraph(dto.getCountry()));
        IBlockElement address = mkInfoWithIcon(CVGenerationModule.HOME_ICON, addressLabel);
        leftCenter.add(address);

        //add email
        Cell emailLabel = new Cell().add(new Paragraph(dto.getEmail()));
        IBlockElement email = mkInfoWithIcon(CVGenerationModule.EMAIL_ICON, emailLabel);
        leftCenter.add(email);

        //add phone number
        Cell phoneLabel = new Cell().add(new Paragraph(dto.getPhone()));
        IBlockElement phone = mkInfoWithIcon(CVGenerationModule.PHONE_ICON, phoneLabel);
        leftCenter.add(phone);


        leftSection.add(leftCenter);


        Cell leftBottom = new Cell();
        leftBottom.setBackgroundColor(grayColor); // Fond gris clair
        leftBottom.setPadding(10);


        //adding birthday
        String birthdayLabel = bundle.getString("birthday.label");
        IBlockElement birthday = mkInfoWithoutIcon(birthdayLabel, dto.getBirthday());
        leftBottom.add(birthday);

        //adding birthplace
        String birthplaceLabel = bundle.getString("birthplace.label");
        IBlockElement birthplace = mkInfoWithoutIcon(birthplaceLabel, dto.getBirthplace());
        leftBottom.add(birthplace);


        //adding birthplace
        String birthdayCountryLabel = bundle.getString("birthCountry.label");
        IBlockElement birthCountry = mkInfoWithoutIcon(birthdayCountryLabel, dto.getCountry());
        leftBottom.add(birthCountry);


        //adding birthplace
        String civilStateLabel = bundle.getString("civilState.label");
        IBlockElement civilState = mkInfoWithoutIcon(civilStateLabel, dto.getCivilState());
        leftBottom.add(civilState);


        //adding birthplace
        String childrenLabel = bundle.getString("children.label");
        IBlockElement children = mkInfoWithoutIcon(childrenLabel, dto.getChildren() + "");
        leftBottom.add(children);

        String nationalityLabel = bundle.getString("nationality.label");
        IBlockElement nationality = mkInfoWithoutIcon(nationalityLabel, dto.getNationality());
        leftBottom.add(nationality);

        IBlockElement space = mkInfoWithoutIcon("", "");
        leftBottom.add(space);
        leftBottom.add(space);
        Cell cell = new Cell();
        cell.setHeight(60);
        leftBottom.add(cell);


        leftSection.add(leftBottom);


        // Add language proficiency bars


        return leftSection.setBorder(Border.NO_BORDER);
    }


    public IBlockElement mkInfoWithIcon(String icon, Cell info) throws CVGenerationException {
        Table table = new Table(new float[]{1, 4});
        table.setBorder(Border.NO_BORDER);

        Cell addressIcon = loadIcon(icon);

        table.addCell(addressIcon);
        table.addCell(info.setBorder(Border.NO_BORDER));
        info.setVerticalAlignment(VerticalAlignment.MIDDLE);


        return table;
    }

    public static IBlockElement mkInfoWithoutIcon(String infoLabel, String info) {
        Cell cell = new Cell();

        cell.add(new Paragraph(infoLabel).setBold());
        cell.add(new Paragraph(info));

        cell.setHeight(50);
        return cell;
    }


    public Cell loadIcon(String path) throws CVGenerationException {
        Cell cell = new Cell();
        cell.setBorder(Border.NO_BORDER);

        Image img = loadImage(path);

        img.setWidth(CVGenerationModule.ICON_SIZE);
        img.setHeight(CVGenerationModule.ICON_SIZE);
        img.setTextAlignment(TextAlignment.CENTER);
        cell.setVerticalAlignment(VerticalAlignment.MIDDLE);
        cell.add(img);
        return cell;
    }

    public Image loadImage(String path) throws CVGenerationException {
        Image img;
        try {
            img = new Image(ImageDataFactory.create(path));
        } catch (MalformedURLException e) {
            throw new CVGenerationException("Error While creating icon:" + e.getMessage());
        }

        return img;
    }
}



