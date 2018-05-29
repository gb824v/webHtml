package chat.logic;

import java.util.ArrayList;
import java.util.List;

public class ChatManager {
    private final List<ChatEntry> chatDataList;

    public ChatManager() {
        chatDataList = new ArrayList<>();
    }

    public void addChatString(String chatString, String username) {
        chatDataList.add(new ChatEntry(chatString, username));
    }

    public List<ChatEntry> getChatEntries(int fromIndex){
        if (fromIndex < 0 || fromIndex >= chatDataList.size()) {
            fromIndex = 0;
        }
        return chatDataList.subList(fromIndex, chatDataList.size());
    }

    public int getVersion() {
        return chatDataList.size();
    }
    
    public static class ChatEntry {
        private final String chatString;
        private final String username;
        private final long time;

        public ChatEntry(String chatString, String username) {
            this.chatString = chatString;
            this.username = username;
            this.time = System.currentTimeMillis();
        }

        public String getChatString() {
            return chatString;
        }

        public long getTime() {
            return time;
        }

        public String getUsername() {
            return username;
        }

        @Override
        public String toString() {
            return (username != null ? username + ": " : "") + chatString;
        }
    }
}