import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class RosterPDF {
    private static final String DESTINATION_FILE_PATH = "Roster.pdf";
    private static final String LOGO_FILE_PATH = "logo.png";

    /**
     * Class constructor
     * creates a PDF document containing club contact info (name, email, phone number)
     * and saves it as Roster.pdf
     * @throws IOException
     */
    public RosterPDF() throws IOException {
        try (PDDocument doc = new PDDocument()) {
            // create a page
            PDPage page = new PDPage();
            // get dimensions of page, set margins
            int pageWidth = (int)page.getTrimBox().getWidth();
            int pageHeight = (int)page.getTrimBox().getHeight();
            int marginTB = 50;
            int marginLR = 75;

            // logo specifications
            PDImageXObject pdImage = PDImageXObject.createFromFile(LOGO_FILE_PATH,doc);
            int logoWidth = 952;
            int logoHeight = 483;
            int reduceByFactor = 5;
            logoWidth /= reduceByFactor;
            logoHeight /= reduceByFactor;
            int logoX = (pageWidth-logoWidth)/2;
            int logoY = pageHeight-logoHeight-marginTB;

            // font stuff
            PDFont font = PDType1Font.COURIER;
            int fontSize = 10; // our preference is 10

            // create date-updated stamp
            LocalDate dateObj = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
            String date = "Updated: " + dateObj.format(formatter);
            int updateX = pageWidth - marginLR - (int) font.getStringWidth(date)/1000 * fontSize;

            // table specifications
            int initX = marginLR;
            int initY = logoY - marginTB/2;
            int deltaY = (int) (1.5 * fontSize);
            int colCount = 3;

            // determine number of pages needed
            int membersPerPage = (logoY - marginTB/2 - marginTB) / deltaY;
            int numPagesNeeded = (Angler.getMembers().size()-1) / membersPerPage + 1;

            // create the necessary number of pages
            ArrayList<PDPage> pageArrayList = new ArrayList<>();
            pageArrayList.add(page);
            for (int i = 1; i < numPagesNeeded; i++) {
                pageArrayList.add(new PDPage());
            }

            // print everything on the pages
            int memberIndex = 0;
            for (PDPage pdPage : pageArrayList) {
                // add each page to the doc
                doc.addPage(pdPage);

                try (PDPageContentStream contents = new PDPageContentStream(doc, pdPage)) {
                    // add logo
                    contents.drawImage(pdImage, logoX, logoY, logoWidth, logoHeight);

                    // add date updated stamp
                    contents.beginText();
                    contents.setLeading(14.5f);
                    contents.setFont(font, fontSize);
                    contents.newLineAtOffset(updateX, (float) marginTB / 2);
                    contents.showText(date);
                    contents.endText();

                    // determine column-widths for the table
                    int recordNameLength = 0;
                    String recordName = "";
                    int recordEmailLength = 0;
                    String recordEmail = "";
                    int stopIndex = Math.min(memberIndex + membersPerPage, Angler.getMembers().size());
                    for (int i = memberIndex; i < stopIndex; i++) {
                        String name = Angler.getMembers().get(i).getName();
                        String email = Angler.getMembers().get(i).getEmail();
                        if (name.length() > recordNameLength) {
                            recordNameLength = name.length();
                            recordName = name;
                        }
                        if (email.length() > recordEmailLength) {
                            recordEmailLength = email.length();
                            recordEmail = email;
                        }
                    }
                    int recordNameWidth = ((int) font.getStringWidth(recordName)) / 1000 * fontSize;
                    int recordEmailWidth = ((int) font.getStringWidth(recordEmail) + 1) / 1000 * fontSize;
                    int phoneWidth = ((int) font.getStringWidth("XXX-XXX-XXXX")) / 1000 * fontSize;
                    int emailX = pageWidth - marginLR - recordEmailWidth;
                    int phoneX = (recordNameWidth + pageWidth - recordEmailWidth - phoneWidth) / 2;

                    // add members and their contact info to the table
                    for (int i = memberIndex; i < stopIndex; i++) {
                        for (int j = 0; j < colCount; j++) {
                            Angler angler = Angler.getMembers().get(i);
                            contents.beginText();
                            contents.newLineAtOffset(initX, initY);
                            if (angler.getIsOfficer())
                                contents.setFont(PDType1Font.COURIER_BOLD, fontSize);
                            else
                                contents.setFont(font, fontSize);
                            if (j == 0) {
                                contents.showText(angler.getName());
                                initX = phoneX;
                            }
                            if (j == 1) {
                                contents.showText(angler.getPhoneNumber());
                                initX = emailX;
                            }
                            if (j == 2) {
                                contents.showText(angler.getEmail());
                                initX = marginLR;
                                initY -= deltaY;
                            }
                            contents.endText();
                        }
                    }
                }
                // reset the member index and the starting point for the table on the next page
                memberIndex += membersPerPage;
                initY = logoY - marginTB/2;
            }
            doc.save(DESTINATION_FILE_PATH);
        }
    }

    public String getDestinationFilePath() { return DESTINATION_FILE_PATH; }
}
