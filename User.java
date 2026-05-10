public class User {
    private String username;
    private boolean online;

    public User(String username) {
        this.username = username;
        this.online = true;
    }

    public String getUsername() {
        return username;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean status) {
        this.online = status;
    }

    @Override
    public String toString() {
        return username + (online ? " (Online)" : " (Offline)");
    }
}