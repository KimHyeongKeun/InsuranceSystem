package exception;

import java.io.BufferedReader;
import java.io.IOException;

import dao.InsuranceDao;
import dao.InsuranceDaoImpl;

public class NoExtend {

    private static InsuranceDao insuranceDao;

    public NoExtend() {
        insuranceDao = new InsuranceDaoImpl();
    }

    public void noExtendComplete(BufferedReader objReader) {
//		◎ <연장> 버튼을 눌렀는데 연장이 되지 않는 경우. E1
//		1. 시스템은 현재 시스템 오류로 다시 시도하시기 바랍니다."라는 메시지와 함께 <확인>버튼을 화면에 보여준다.
//		2. 보험 영업 사원은 <확인>버튼을 누른다.
//		3. 시스템은 Build Path 4번으로 이동한다.
        try {
            System.out.println("현재 시스템 오류로 다시 시도하시기 바랍니다.");
            System.out.println("1.확인");
            String button = objReader.readLine();
            insuranceDao.extendComplete(null);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
