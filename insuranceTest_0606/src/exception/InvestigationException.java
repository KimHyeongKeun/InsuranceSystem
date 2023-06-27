package exception;

import java.io.BufferedReader;
import java.io.IOException;

public class InvestigationException {

    public InvestigationException() {

    }

    public void NoFileUpload(BufferedReader objReader) throws IOException {
        System.out.println("시스템 오류로 인해 사고 접수가 정상 수행되지 않았습니다. 다시 시도해주세요");
        System.out.println("1.확인 ");


        String okayButton = objReader.readLine().trim();    //확인 클릭
    }

}
