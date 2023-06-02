package MVC.M;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.LinkedList;
import java.util.List;

public class FileWriter {
    private final long RECORD_SIZE;
    protected File dataFile;
    protected RandomAccessFile randFile;

    public FileWriter(long recordSize, String path) {
        try {
            dataFile = new File(path);
            dataFile.delete();
            randFile = new RandomAccessFile(dataFile, "rw");
        } catch (IOException e) {
            System.out.println("MAKE SURE A FOLDER NAMED \"Data\" IS AVAILABLE IN CURRENT FOLDER");
            throw new RuntimeException(e);
        }
        this.RECORD_SIZE = recordSize;
    }

    protected int getRecordAmount() {
        return (int) (dataFile.length() / RECORD_SIZE);
    }

    protected boolean appendRecord(String dataLine) {
        try {
            randFile.seek(randFile.length());
            randFile.writeBytes(dataLine);
            return true;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected void overrideRecord(String newData, int idx) throws IOException {
        randFile.seek((long) (idx - 1) * RECORD_SIZE);
        randFile.writeBytes(newData);
    }

    protected String readRecord(int idx) throws IOException {
        randFile.seek((idx - 1) * RECORD_SIZE);
        return byteToString((int) RECORD_SIZE);
    }

    protected List<String> readAllRecords() throws IOException {
        randFile.seek(0);
        List<String> allRecords = new LinkedList<>();
        int recordCount = getRecordAmount();
        for (int i = 0; i < recordCount; i++) {
            allRecords.add(readRecord(i + 1));
        }
        return allRecords;
    }

    protected void writeMultipleRecords(List<String> dataList) {
        dataList.forEach(this::appendRecord);
    }

    protected String[] gatherFieldSpecific(int relativeIdx, int fieldSize) throws IOException {
        randFile.seek(0);
        String[] allData = new String[getRecordAmount()];
        for (int i = 0; i < getRecordAmount(); i++) {
            randFile.seek(i * RECORD_SIZE + relativeIdx);
            allData[i] = byteToString(fieldSize);
        }
        return allData;
    }

    private String byteToString(int nByte) {
        byte[] data = new byte[nByte];
        try {
            randFile.readFully(data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        StringBuilder output = new StringBuilder();
        for (byte datum : data) {
            output.append((char) datum);
        }
        return output.toString();
    }

    protected int findFieldSpecific(int relativeIdx, int fieldSize, String matchingLine) throws IOException {
        for (int i = 0; i < getRecordAmount(); i++) {
            randFile.seek(i * RECORD_SIZE + relativeIdx);
            if (byteToString(fieldSize).strip().equals(matchingLine)) return i + 1;
        }
        return -1;
    }

    protected void removeDataLine(int idx) throws IOException {
        File tempFile = new File("TEMP" + dataFile.getName());

        if (tempFile.exists())
            if (tempFile.delete()) System.out.println("AH YES DADDY");

        RandomAccessFile randTempFile = new RandomAccessFile(tempFile, "rw");

        randFile.seek(0);
        for (int i = 0; i < (idx - 1) * RECORD_SIZE; i++) {
            randTempFile.writeByte(randFile.read());
        }
        randFile.seek(randFile.getFilePointer() + RECORD_SIZE);

        int remaining = (int) (randFile.length() - randFile.getFilePointer());
        for (int i = 0; i < remaining; i++) {
            randTempFile.writeByte(randFile.read());
        }

        randFile.close();
        randTempFile.close();

        if (dataFile.delete())
            if (tempFile.renameTo(dataFile)) {
                randFile = new RandomAccessFile(dataFile, "rw");
                return;
            }

        System.exit(5896);
    }
}
