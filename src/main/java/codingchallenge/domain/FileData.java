package codingchallenge.domain;

public class FileData {

    private String name;
    private String path;
    private byte[] bytes;

    public FileData(String name, String path, byte[] bytes) {
        this.name = name;
        this.path = path;
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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
