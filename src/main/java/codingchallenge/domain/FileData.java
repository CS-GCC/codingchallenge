package codingchallenge.domain;

public class FileData {

    private String name;
    private byte[] bytes;

    public FileData(String name, byte[] bytes) {
        this.name = name;
        this.bytes = bytes;
    }

    public FileData() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }
}
