package MVC.M;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.LinkedList;
import java.util.List;

public class FileWriter {
    private final long RECORD_SIZE;
    protected File dataFile;
    protected RandomAccessFile randFile;

    public FileWriter(long recordSize, String path) {
        this.dataFile = new File(path);
        if (dataFile.exists()) dataFile.delete();
        try {
            dataFile.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            randFile = new RandomAccessFile(dataFile, "rw");
        } catch (FileNotFoundException e) {
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
            return false;
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
        for (int i = 0; i < getRecordAmount(); i++) {
            allRecords.add(readRecord(i));
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
            if (byteToString(fieldSize).equals(matchingLine)) return i + 1;
        }
        return -1;
    }

    protected void removeDataLine(int idx) throws IOException {
        File tempFile = new File("TEMP" + dataFile.getName());
        RandomAccessFile randTempFile = new RandomAccessFile(tempFile, "rw");
        if (!tempFile.createNewFile()) System.exit(85);

        randFile.seek(0);
        for (int i = 0; i < (idx - 1) * RECORD_SIZE; i++) {
            randTempFile.writeByte(randFile.read());
        }
        randFile.seek(randFile.getFilePointer() + RECORD_SIZE);
        for (int i = 0; i < randFile.length() - tempFile.length(); i++) {
            randTempFile.writeByte(randFile.read());
        }

        randFile.close();
        randTempFile.close();

        if (dataFile.delete())
            if (tempFile.renameTo(dataFile)) {
                dataFile = tempFile;
                randFile = new RandomAccessFile(dataFile, "rw");
                return;
            }

        System.exit(85);
    }
}
