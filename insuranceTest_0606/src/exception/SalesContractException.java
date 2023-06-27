package exception;

import java.io.BufferedReader;
import java.io.IOException;

public class SalesContractException {
    int submenu;

    public SalesContractException() {
        submenu = 0;
    }

    public void customerEmptyException(BufferedReader objReader) {
        try {
            System.out.println("죄송합니다! 현재 시스템에 오류가 발생했습니다. 기술팀에 문의 주시거나 다시 시도해주시기 바랍니다.");
            System.out.println("1-확인");
            submenu = Integer.parseInt(objReader.readLine());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void nullMenuException() {
        System.out.println("메뉴를 미입력하셨습니다.");
    }
}