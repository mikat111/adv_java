public class Notification {

    public static void sendMessages(String... messages) {

        System.out.println("Notifications:");

        for (String msg : messages) {
            System.out.println(msg);
        }
    }
}