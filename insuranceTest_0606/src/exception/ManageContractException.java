package exception;

import java.io.BufferedReader;
import java.io.IOException;

public class ManageContractException {

    public ManageContractException() {

    }

    public void NotValuableExtend(BufferedReader objReader) throws IOException {
        System.out.println("해당 연도는 갱신 불가능한 연도 범위입니다. 다시 메뉴를 입력해주세요.");
        System.out.println("1. 확인");
        String okayButton = objReader.readLine().trim();    //확인 클릭
    }

    public void NoExtend() {

    }

}
