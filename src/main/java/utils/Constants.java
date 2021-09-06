package utils;

public class Constants {

    public static final String AccountUsername = "Enter your own username";
    public static final String AccountPassword = "Enter your own password";
    public static final String CommentTagFilter = "Lotr";
    public static String[] arrayTags = new String[]{
            "barcelona"
    };
    public final static String[] arrayComments = new String[]{
            "Nice",
            "Nice one :)",
            "Lookin good !"
    };
    public final static String ProjectPath = System.getProperty("user.dir");
    public final static String Filepath = (ProjectPath + "\\Datas\\followedUsers_" + System.currentTimeMillis() + ".xls");
}
