package com.lautadev.juegos_deportivos.util;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.lautadev.juegos_deportivos.dto.InscriptionDTO;
import com.lautadev.juegos_deportivos.dto.ParticipantDTO;
import com.lautadev.juegos_deportivos.model.enums.SportRole;
import com.sun.tools.javac.Main;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import java.util.List;

public class PDFGenerator {

    public static void GeneratePdf(InscriptionDTO inscriptionDTO){
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
            String formattedDate = inscriptionDTO.getInscriptionDate().format(formatter);
            Document document = new Document();
            String destino = "inscription_"+formattedDate+".pdf";
            PdfWriter.getInstance(document, new FileOutputStream(destino));
            document.open();

            // START Font Styles
            Font titleFont = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD);
            Font subTitleFont = new Font(Font.FontFamily.HELVETICA, 12);
            Font sectionFont = new Font(Font.FontFamily.HELVETICA, 12,Font.BOLD);
            Font infoFont = new Font(Font.FontFamily.HELVETICA, 11);

            Font headerFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD); // Header table

            Font signatureFont = new Font(Font.FontFamily.HELVETICA, 12);
            // END Font Styles

            // Document Title
            Paragraph title = new Paragraph("FICHA DE INSCRIPCIÓN 2018 N° "+inscriptionDTO.getId(), titleFont);
            title.setAlignment(Paragraph.ALIGN_CENTER);
            document.add(title);

            // Document Subtitle
            Paragraph subTitle = new Paragraph("DEPORTE: "+ inscriptionDTO.getDiscipline().getName() +
                                                " – CATEGORÍA: "+ inscriptionDTO.getDiscipline().getCategory().getName()+" – " +
                                                        "GÉNERO – "+ inscriptionDTO.getGender() +
                                                    " – POSADAS", subTitleFont);
            subTitle.setAlignment(Paragraph.ALIGN_CENTER);
            document.add(subTitle);

            // Section Institution
            Paragraph institution = new Paragraph("INSTITUTION", sectionFont);
            institution.setAlignment(Paragraph.ALIGN_CENTER);
            document.add(institution);

            // INFO Institution
            Paragraph additionalInfo = new Paragraph();
            additionalInfo.setFont(infoFont);
            additionalInfo.add("Nombre: "+inscriptionDTO.getInstitution().getName()+"\n");
            additionalInfo.add("Dirección: "+inscriptionDTO.getInstitution().getDomicile()+"\n");
            additionalInfo.add("Localidad:  "+inscriptionDTO.getInstitution().getMunicipality()+"\n");
            additionalInfo.add("Teléfono: "+inscriptionDTO.getInstitution().getCel());
            document.add(additionalInfo);

            // Section Sportsman
            Paragraph sportsman = new Paragraph("DEPORTISTAS",sectionFont);
            sportsman.setAlignment(Paragraph.ALIGN_CENTER);
            document.add(sportsman);

            // Info Table Sportsman
            PdfPTable tableSportmans = new PdfPTable(5);
            tableSportmans.setWidthPercentage(100);
            tableSportmans.setSpacingBefore(10); // Add space before the table

            PdfPCell cell = new PdfPCell(new Phrase("N°", headerFont));
            cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            tableSportmans.addCell(cell);

            cell = new PdfPCell(new Phrase("Apellido y Nombre", headerFont));
            cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            tableSportmans.addCell(cell);

            cell = new PdfPCell(new Phrase("N° Documento", headerFont));
            cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            tableSportmans.addCell(cell);

            cell = new PdfPCell(new Phrase("Fecha de Nacimiento", headerFont));
            cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            tableSportmans.addCell(cell);

            cell = new PdfPCell(new Phrase("Localidad", headerFont));
            cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            tableSportmans.addCell(cell);

            // Set column widths (proportional to the total table width)
            float[] columnWidths = {0.5f, 2f, 2f, 2f, 2f};
            tableSportmans.setWidths(columnWidths);

            // Filter participants with SportRole DEPORTISTA
            List<ParticipantDTO> sportmans = inscriptionDTO.getParticipantDTOS().stream()
                    .filter(participantDTO -> SportRole.DEPORTISTA.equals(participantDTO.getSportRole()))
                    .collect(Collectors.toList());

            int indexTableSportsman = 1;
            for (ParticipantDTO sportman : sportmans) {
                addCenteredCell(tableSportmans, String.valueOf(indexTableSportsman++)); // N°
                addCenteredCell(tableSportmans, sportman.getLastname() + " "+sportman.getName()); // Apellido y Nombre
                addCenteredCell(tableSportmans, sportman.getDni()); // N° Documento
                addCenteredCell(tableSportmans, sportman.getDateOfBirth().toString()); // Fecha de Nacimiento
                addCenteredCell(tableSportmans, sportman.getMunicipality().toString()); // Localidad
            }

            // Add the table to the document
            tableSportmans.setSpacingAfter(20);  // Add space after the table
            document.add(tableSportmans);

            // Section Instructors
            Paragraph Instructors = new Paragraph("INSTRUCTORES",sectionFont);
            Instructors.setAlignment(Paragraph.ALIGN_CENTER);
            document.add(Instructors);

            // Info Table Instructors
            PdfPTable tableInstructor = new PdfPTable(5);
            tableInstructor.setWidthPercentage(100);
            tableInstructor.setSpacingBefore(20);

            PdfPCell cellInsctructor = new PdfPCell(new Phrase("N°", headerFont));
            cellInsctructor.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            tableInstructor.addCell(cellInsctructor);

            cellInsctructor = new PdfPCell(new Phrase("Apellido y Nombre", headerFont));
            cellInsctructor.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            tableInstructor.addCell(cellInsctructor);

            cellInsctructor = new PdfPCell(new Phrase("N° Documento", headerFont));
            cellInsctructor.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            tableInstructor.addCell(cellInsctructor);

            cellInsctructor = new PdfPCell(new Phrase("Teléfono", headerFont));
            cellInsctructor.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            tableInstructor.addCell(cellInsctructor);

            cellInsctructor = new PdfPCell(new Phrase("Email", headerFont));
            cellInsctructor.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            tableInstructor.addCell(cellInsctructor);

            // Filter participants with SportRole INSTRUCTOR
            List<ParticipantDTO> instructors = inscriptionDTO.getParticipantDTOS().stream()
                    .filter(participantDTO -> SportRole.INSTRUCTOR.equals(participantDTO.getSportRole()))
                    .collect(Collectors.toList());

            int indexTableInstructor = 1;
            for (ParticipantDTO instructor : instructors) {
                addCenteredCell(tableInstructor, String.valueOf(indexTableInstructor++)); // N°
                addCenteredCell(tableInstructor, instructor.getLastname() + " "+instructor.getName()); // Apellido y Nombre
                addCenteredCell(tableInstructor, instructor.getDni()); // N° Documento
                addCenteredCell(tableInstructor, instructor.getCel()); // Telefono
                addCenteredCell(tableInstructor, instructor.getEmail()); // Email
            }

            tableInstructor.setWidths(columnWidths);
            tableInstructor.setSpacingAfter(20);
            document.add(tableInstructor);

            // Section Team Leaders
            Paragraph responsible = new Paragraph("RESPONSABLES DE EQUIPO",sectionFont);
            responsible.setAlignment(Paragraph.ALIGN_CENTER);
            document.add(responsible);

            // Info Table Team Leaders
            PdfPTable tableResponsible = new PdfPTable(5);
            tableResponsible.setWidthPercentage(100); // Set the table width to 100% of the page width
            tableResponsible.setSpacingBefore(20);

            PdfPCell cellResponsible = new PdfPCell(new Phrase("N°", headerFont));
            cellResponsible.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            tableResponsible.addCell(cellResponsible);

            cellResponsible = new PdfPCell(new Phrase("Apellido y Nombre", headerFont));
            cellResponsible.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            tableResponsible.addCell(cellResponsible);

            cellResponsible = new PdfPCell(new Phrase("N° Documento", headerFont));
            cellResponsible.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            tableResponsible.addCell(cellResponsible);

            cellResponsible = new PdfPCell(new Phrase("Teléfono", headerFont));
            cellResponsible.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            tableResponsible.addCell(cellResponsible);

            cellResponsible = new PdfPCell(new Phrase("Email", headerFont));
            cellResponsible.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            tableResponsible.addCell(cellResponsible);

            // Filter participants with SportRole RESPONSABLES_DE_EQUIPO
            List<ParticipantDTO> teamLeaders = inscriptionDTO.getParticipantDTOS().stream()
                    .filter(participantDTO -> SportRole.RESPONSABLE_DE_EQUIPO.equals(participantDTO.getSportRole()))
                    .collect(Collectors.toList());

            int indexTableTeamLead = 1;
            for (ParticipantDTO teamLeader : teamLeaders) {
                addCenteredCell(tableResponsible, String.valueOf(indexTableTeamLead++)); // N°
                addCenteredCell(tableResponsible, teamLeader .getLastname() + " "+teamLeader .getName()); // Apellido y Nombre
                addCenteredCell(tableResponsible, teamLeader .getDni()); // N° Documento
                addCenteredCell(tableResponsible, teamLeader .getCel()); // Telefono
                addCenteredCell(tableResponsible, teamLeader .getEmail()); // Email
            }

            tableResponsible.setWidths(columnWidths);
            tableResponsible.setSpacingAfter(20);
            document.add(tableResponsible);

            // Section Signatures
            Paragraph signatureSection = new Paragraph();
            signatureSection.setFont(signatureFont);
            signatureSection.setSpacingBefore(20); // Add space before signature section
            signatureSection.add("_________________           _________________           _________________\n");
            signatureSection.add("         Responsable 1                    Responsable 2               Firma Autoridad Institución");
            signatureSection.setAlignment(Paragraph.ALIGN_CENTER);
            signatureSection.setSpacingBefore(120);
            document.add(signatureSection);

            // Section Enroller
            Paragraph enroller = new Paragraph("Inscribe: "+ inscriptionDTO.getEnroller().getLastname() + " "+ inscriptionDTO.getEnroller().getName()
                                                    +" - D.N.I.: "+ inscriptionDTO.getEnroller().getDni(),titleFont);
            enroller.setSpacingBefore(20);
            document.add(enroller);

            Paragraph dataEnroller = new Paragraph("Teléfono: "+ inscriptionDTO.getEnroller().getCel()
                                                        +" – Email: "+ inscriptionDTO.getEnroller().getEmail(),titleFont);
            document.add(dataEnroller);

            document.close();
            System.out.println("PDF created successfully");

        } catch (FileNotFoundException | DocumentException ex) {
            Logger.getLogger(PDFGenerator.class.getName()).log(Level.SEVERE, null, "error");
        }
    }

    // Helper method to create a centered cell
    private static void addCenteredCell(PdfPTable table, String content) {
        PdfPCell cell = new PdfPCell(new Phrase(content));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        table.addCell(cell);
    }
}
