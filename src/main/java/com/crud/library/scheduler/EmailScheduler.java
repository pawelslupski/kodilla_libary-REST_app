package com.crud.library.scheduler;

import com.crud.library.config.AdminConfig;
import com.crud.library.domain.Mail;
import com.crud.library.repository.BorrowingDao;
import com.crud.library.repository.CopyDao;
import com.crud.library.repository.ReaderDao;
import com.crud.library.repository.TitleDao;
import com.crud.library.service.DbService;
import com.crud.library.service.SimpleEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class EmailScheduler {
    private static final String SUBJECT = "Library: Once a day notification";

    @Autowired
    DbService dbService;
    @Autowired
    ReaderDao readerDao;
    @Autowired
    TitleDao titleDao;
    @Autowired
    CopyDao copyDao;
    @Autowired
    BorrowingDao borrowingDao;
    @Autowired
    private SimpleEmailService emailService;
    @Autowired
    private AdminConfig adminConfig;

    @Scheduled(cron = "0 0 10 * * *")
    public void sendNotificationEmail() {
        long totalReader = readerDao.count();
        long totalTittle = titleDao.count();
        long totalCopy = copyDao.count();
        long totalBorrowing = borrowingDao.count();

        emailService.send(new Mail(adminConfig.getAdminMail(), SUBJECT, "Current totals: " + "\n" +
                totalReader + " - registered " + (totalReader == 1 ? "reader":"readers") + "\n" +
                totalTittle + " - registered " + (totalTittle == 1 ? "title":"titles") + "\n" +
                totalCopy + " - registered " + (totalCopy == 1 ? "copy":"copies") + "\n" +
                totalBorrowing + " - registered " + (totalBorrowing == 1 ? "borrowing":"borrowings") + "\n" +
                "in library database."));
    }
}
