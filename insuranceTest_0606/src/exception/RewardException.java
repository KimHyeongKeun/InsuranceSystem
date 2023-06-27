package exception;

import java.io.BufferedReader;
import java.io.IOException;

public class RewardException extends Exception {

    public void NoLinkedList(BufferedReader objReader) throws IOException {
        System.out.println("사고 정보에 누락된 내용이 있어 심사를 진행할 수 없습니다.");
    }

    public void NoSiteInfo(BufferedReader objReader) throws IOException {
        System.out.println("시스템 오류로 인해 첨부파일이 정상적으로 조회되지 않습니다. 시스템을 재시작 하시기 바랍니다");
    }

    public void NoExemptionInfo(BufferedReader objReader) throws IOException {
        System.out.println("시스템 로딩에 오류가 발생하였습니다. 다시 시도해주세요");
    }
}
