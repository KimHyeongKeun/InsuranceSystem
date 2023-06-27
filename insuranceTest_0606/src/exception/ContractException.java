package exception;

import java.io.BufferedReader;
import java.io.IOException;

public class ContractException {

    public ContractException() {

    }

    public void NoNameList(BufferedReader objReader) throws IOException {
        System.out.println("리스트에 이름이 없습니다. 다시 입력하세요.");
    }

    public void NotSendEmail(BufferedReader objReader) throws IOException {
        System.out.println("메일 발송에 오류가 생겼습니다");
    }
}
