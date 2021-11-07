package com.epolsoft.brest.service.impl;

import com.epolsoft.brest.dao.api.FileNameDao;
import com.epolsoft.brest.dao.api.PersonDao;
import com.epolsoft.brest.exception.DataNotFoundException;
import com.epolsoft.brest.exception.ReadFormatNotSupportedException;
import com.epolsoft.brest.file.api.FileOfPersonReader;
import com.epolsoft.brest.model.CSVFile;
import com.epolsoft.brest.model.Person;

import com.epolsoft.brest.service.api.ResaveService;
import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class DbResaveService implements ResaveService {

    private final PersonDao personDao;
    private final FileOfPersonReader fileOfPersonReader;
    private final FileNameDao fileNameDao;

    private CSVFile csvFile;
    private boolean isNewFile;
    private Date beforeLastModified;

    private static final Logger logger = Logger.getLogger(DbResaveService.class);

    public DbResaveService(PersonDao personDao, FileOfPersonReader fileOfPersonReader, FileNameDao fileNameDao) {
        this.personDao = personDao;
        this.fileOfPersonReader = fileOfPersonReader;
        this.fileNameDao = fileNameDao;
        this.isNewFile = false;
    }

    @Override
    @Scheduled(cron = "0/60 * * * * ?")
    public void service() {
        if (isNewFile) {
            save();
            isNewFile = false;
        } else if (csvFile != null) {
            if (beforeLastModified == null || beforeLastModified.before(csvFile.getLastModified())) {
                save();
            } else {
                logger.info(csvFile.getFilename() + ".CSV был прочитан ранее, нет новых данных");
            }
        } else {
            logger.info("не удалось прочитать путь к CSV файлу");
        }
    }

    private void save() {
        try {
            List<Person> people = fileOfPersonReader.readData(csvFile.getFilename());
            people = deleteIfExistInDb(people);
            beforeLastModified = csvFile.getLastModified();
            if (people.isEmpty()) {
                logger.info(csvFile.getFilename() + ".CSV был прочитан ранее, нет новых данных");
            } else {
                personDao.saveAll(people);
                logger.info(csvFile.getFilename() + ".CSV прочитан и сохранен");
            }
        } catch (ReadFormatNotSupportedException e) {
            beforeLastModified = null;
            logger.info(e.getMessage());
        }
    }

    @Override
    @Scheduled(cron = "0/30 * * * * ?")
    public void readFileName() {
        String filename;
        try {
            filename = fileNameDao.read(1);
            File file = new File(filename + ".csv");
            Date lastModified = new Date(file.lastModified());
            if (csvFile == null || !csvFile.getFilename().equals(filename)) {
                csvFile = new CSVFile(filename, lastModified);
                beforeLastModified = null;
                isNewFile = true;
            } else {
                csvFile.setLastModified(lastModified);
            }
        } catch (DataNotFoundException e) {
            csvFile = null;
            isNewFile = false;
        }
    }

    private List<Person> deleteIfExistInDb(List<Person> peopleInCsv) {
        List<Person> newPeople = new ArrayList<>();
        for (Person person : peopleInCsv) {
            if (personDao.findInDb(person) == 0) {
                newPeople.add(person);
            }
        }
        return newPeople;
    }
}
