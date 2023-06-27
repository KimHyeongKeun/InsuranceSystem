package exception;

public class EmailNotAvailableException extends Exception {
    public EmailNotAvailableException(String message) {
        super(message);
    }

    public void sendNotification() {
        System.out.println("해당 고객에게 이메일이 저장되어 있지 않기에, 문자만 전송하였습니다!");
        timer(4);
        System.out.println("\n정상적으로 알림을 전송하였습니다!");
    }

    public static void timer(int count) {
        try {
            for (int time = 1; time <= count; time++) {
                System.out.print(time + ".. ");
                Thread.sleep(1000);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
