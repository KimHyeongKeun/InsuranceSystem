package UI;

import accident.Accident;
import accident.AccidentList;
import accident.AccidentListImpl;
import accident.SiteInfo;
import contract.Contract;
import contract.ContractList;
import contract.ContractListImpl;
import customer.Customer;
import customer.CustomerList;
import customer.CustomerListImpl;
import dao.*;
import employee.Employee;
import employee.EmployeeList;
import exception.*;
import exemption.Exemption;
import exemption.ExemptionList;
import exemption.ExemptionListImpl;
import insurance.*;
import mail.MailSender;
import pCustomer.PCustomer;
import pCustomer.PCustomerList;
import pCustomer.PCustomerListImpl;
import reward.RewardInfo;

import javax.mail.MessagingException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.RemoteException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static exception.EmailNotAvailableException.timer;

public class ISMain {
    private static Employee employee;
    private static InsuranceList insuranceList;
    private static CustomerList customerList;
    private static PCustomerList pCustomerList;
    private static AccidentList accidentList;
    private static EmployeeList employeeList;
    private static ContractList contractList;
    private static Scanner sc;

    // DAO
    private static AccidentDao accidentDao;
    private static ContractDao contractDao;
    private static CustomerDao customerDao;
    private static BuildingDao buildingDao;
    private static CarDao carDao;
    private static DriverDao driverDao;
    private static InsuranceDao insuranceDao;
    private static SaleRecordDao saleRecordDao;
    private static CoverageDao coverageDao;
    private static ApproveDao approveDao;
    private static PCustomerDao pCustomerDao;
    private static ExemptionDao exemptionDao;
    private static DamageDao damageDao;
    private static EmployeeDao employeeDao;

    // exception
    private static SalesContractException scException;
    private static NoExtend noExtend;
    private static ManageContractException manageContractException;
    private static ContractException contractException;
    private static InvestigationException investigationException;
    private static RewardException rewardException;
    private static AccidentAcceptException aaException;

    public ISMain() {
        pCustomerList = new PCustomerListImpl();
        customerList = new CustomerListImpl();
        contractList = new ContractListImpl();

        // DAO Impl
        accidentDao = new AccidentDaoImpl();
        contractDao = new ContractDaoImpl();
        customerDao = new CustomerDaoImpl();
        buildingDao = new BuildingDaoImpl();
        carDao = new CarDaoImpl();
        driverDao = new DriverDaoImpl();
        insuranceDao = new InsuranceDaoImpl();
        saleRecordDao = new SaleRecordDaoImpl();
        coverageDao = new CoverageDaoImpl();
        approveDao = new ApproveDaoImpl();
        pCustomerDao = new PCustomerDaoImpl();
        exemptionDao = new ExemptionDaoImpl();
        damageDao = new DamageDaoImpl();
        employeeDao = new EmployeeDaoImpl();

        //exception
        scException = new SalesContractException();
        rewardException = new RewardException();
        aaException = new AccidentAcceptException();
        noExtend = new NoExtend();
        manageContractException = new ManageContractException();
        contractException = new ContractException();
        investigationException = new InvestigationException();
    }

    public static void setDBData() {
        pCustomerList = pCustomerDao.retrieve();
        customerList = customerDao.retrieve();
        insuranceList = insuranceDao.retrieve();
        accidentList = accidentDao.retrieve();
        contractList = contractDao.retrieve();
        employeeList = employeeDao.retrieve();
    }

    public static void refreshDBData(String type) {
        switch (type) {
            case "pcustomer":
                pCustomerList = pCustomerDao.retrieve();
                break;
            case "customer":
                customerList = customerDao.retrieve();
                break;
            case "insurance":
                insuranceList = insuranceDao.retrieve();
                break;
            case "contract":
                contractList = contractDao.retrieve();
                break;
            case "employee":
                employeeList = employeeDao.retrieve();
                break;
        }
    }

    public static void main(String[] args) throws IOException, RewardException {
        ISMain main = new ISMain();
        setDBData();
        boolean flag = false;
        BufferedReader objReader = new BufferedReader(new InputStreamReader(System.in));
        try {
            while (true) {
                checkEmployee();
                String sChoice = objReader.readLine().trim();
                switch (sChoice) {
                    case "1":
                        System.out.println("========== 보험 개발 메뉴 ==========");
                        System.out.println("1. 보험 설계하기");
                        System.out.println("2. 상품 인가하기");
                        System.out.println("3. 사후 관리하기");
                        System.out.println("4. 종료하기");
                        int submenu = Integer.parseInt(objReader.readLine());
                        switch (submenu) {
                            case 1:
                                System.out.println("******* 보험상품을 설계하는 페이지입니다 *******");
                                designInsurance(objReader);
                                break;
                            case 2:
                                System.out.println("******* 보험상품을 인가하는 페이지입니다 *******");
                                approveInsurance(objReader);
                                break;
                            case 3:
                                System.out.println("******* 보험상품을 사후관리하는 페이지입니다 ********");
                                manageInsurance(objReader);
                                break;
                            case 4:
                                System.out.println("프로그램을 종료합니다.");
                                flag = true;
                                break;
                        }
                        break;
                    case "2":
                        System.out.println("========== 보험 영업 및 계약 메뉴 ==========");
                        System.out.println("1. 보험료 관리하기");
                        System.out.println("2. 잠재 고객 관리하기");
                        System.out.println("3. 고객 관리하기");
                        System.out.println("4. 계약 관리하기");
                        System.out.println("5. 계약 체결하기");
                        System.out.println("6. 잠재고객임의추가");
                        submenu = Integer.parseInt(objReader.readLine());
                        switch (submenu) {
                            case 1:
                                manageInsurancepremium(objReader);
                                break;
                            case 2:
                                consult(objReader);
                                break;
                            case 3:
                                manageCustomer(objReader);
                                break;
                            case 4:
                                manageContract(objReader);
                                break;
                            case 5:
                                contract(objReader);
                                break;
                            case 6:
                                PCustomer pcustomer = new PCustomer();
                                pcustomer.setDate("2023.05.08");
                                pcustomer.setPhoneNumber("01058830056");
                                pcustomer.setConsultContext("");
                                pcustomer.setCustomerName("박승연");
                                pCustomerDao.create(pcustomer);
                                break;
                        }
                        break;
                    case "3":
                        System.out.println("========== 보험 보상 메뉴 ==========");
                        System.out.println("1. 고객 정보 확인");
                        System.out.println("2. 사고접수 상담");
                        System.out.println("3. 사고상황 조사");
                        System.out.println("4. 면/부책 판단하기");
                        System.out.println("5. 손해사정");
                        System.out.println("6. 종료하기");
                        int submenu3 = Integer.parseInt(objReader.readLine());
                        switch (submenu3) {
                            case 1:
                                checkCustomerInfo(objReader);
                                break;
                            case 2:
                                break;
                            case 3:
                                accidentnvestigation(objReader);
                                break;
                            case 4:
                                decideExemption(objReader);
                                break;
                            case 5:
                                damageAssessment(objReader);
                                break;
                            case 6:
                                System.out.println("프로그램을 종료합니다.");
                                flag = true;
                                break;
                        }
                        break;
                    case "x":
                        return;
                    default:
                        System.out.println("Invalid Choice !!!");
                }
                if (flag == true) break;
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    private static void checkEmployee() {
        System.out.println("****************** CHECK EMPLOYEE *******************");
        System.out.println("접근하고자 하는 부서의 파트를 입력하세요.");
        System.out.println("1. 보험 개발 파트");
        System.out.println("2. 보험 영업 파트");
        System.out.println("3. 보험 보상 파트");
        System.out.println("x. EXIT");
    }

    private static void designInsurance(BufferedReader objReader) throws IOException {
        Insurance insurance = new Insurance();
        System.out.println("======= 보험 설계를 시작하겠습니다. =======");
        System.out.println("설계하고자 하는 보험의 유형을 선택해주세요.");
        System.out.println("1. 자동차 보험");
        System.out.println("2. 운전자 보험");
        System.out.println("3. 건물 화재보험");

        boolean validInput = false;
        while (!validInput) {
            String insuranceType = objReader.readLine().trim();
            switch (insuranceType) {
                case "1":
                    insurance.setInsuranceType("car");
                    System.out.println("[자동차 보험] 유형을 선택하셨습니다.");
                    validInput = true;
                    break;
                case "2":
                    insurance.setInsuranceType("driver");
                    System.out.println("[운전자 보험] 유형을 선택하셨습니다.");
                    validInput = true;
                    break;
                case "3":
                    insurance.setInsuranceType("building");
                    System.out.println("[건물 화재] 유형을 선택하셨습니다.");
                    validInput = true;
                    break;
                default:
                    System.out.println("잘못 입력하셨습니다. 입력하신 값은 " + insuranceType + "입니다. 설계하고자 하는 보험 유형을 다시 확인해주세요.");
                    break;
            }
        }

        System.out.println("보험명을 입력하세요.");
        insurance.setInsuranceName(objReader.readLine());
        System.out.println("보험 설명을 입력하세요.");
        insurance.setContents(objReader.readLine());
        System.out.println("보험 기본 가입비를 입력하세요.");
        insurance.setInsuranceCost(objReader.readLine());
        int insuranceID = insuranceDao.create(insurance);
        if (insuranceID == 0) return;
        insurance.setInsuranceID(insuranceID);

        Coverage hcoverage = new Coverage();
        hcoverage.setCoverageCondition("high");
        System.out.println("사고위험 정도가 높은 경우(HIGH) 보장 내용을 입력하세요.");
        hcoverage.setCoverageContent(objReader.readLine());
        System.out.println("사고위험 정도가 높은 경우(HIGH) 최대 보장 금액을 입력하세요.");
        boolean validHighCostInput = false;
        while (!validHighCostInput) {
            try {
                processCoverage(objReader, hcoverage);
                validHighCostInput = true;
            } catch (InvalidCostEnterException e) {
                System.out.println(e.getMessage());
            }
        }
        hcoverage.setInsuranceID(insuranceID);
        hcoverage.setCoverageCost(insuranceID);
        int hcoverageID = coverageDao.create(hcoverage);
        hcoverage.setCoverageID(hcoverageID);
        insurance.setM_hcoverage(hcoverage);
        Coverage mcoverage = new Coverage();
        mcoverage.setCoverageCondition("middle");
        System.out.println("사고위험 정도가 중간인 경우(MIDDLE) 보장 내용을 입력하세요.");
        mcoverage.setCoverageContent(objReader.readLine());
        System.out.println("사고위험 정도가 중간인 경우(MIDDLE) 최대 보장 금액을 입력하세요.");
        boolean validMidCostInput = false;
        while (!validMidCostInput) {
            try {
                processCoverage(objReader, mcoverage);
                validMidCostInput = true;
            } catch (InvalidCostEnterException e) {
                System.out.println(e.getMessage());
            }
        }
        mcoverage.setInsuranceID(insuranceID);
        int mcoverageID = coverageDao.create(mcoverage);
        mcoverage.setCoverageID(mcoverageID);
        insurance.setM_mcoverage(mcoverage);
        Coverage lcoverage = new Coverage();
        lcoverage.setCoverageCondition("low");
        System.out.println("사고위험 정도가 낮은 경우(LOW) 보장 내용을 입력하세요.");
        lcoverage.setCoverageContent(objReader.readLine());
        System.out.println("사고위험 정도가 낮은 경우(LOW) 최대 보장 금액을 입력하세요.");
        boolean validLowCostInput = false;
        while (!validLowCostInput) {
            try {
                processCoverage(objReader, lcoverage);
                validLowCostInput = true;
            } catch (InvalidCostEnterException e) {
                System.out.println(e.getMessage());
            }
        }
        lcoverage.setInsuranceID(insuranceID);
        int lcoverageID = coverageDao.create(lcoverage);
        lcoverage.setCoverageID(lcoverageID);
        insurance.setM_lcoverage(lcoverage);

        insuranceList.add(insurance);
        System.out.println("보험 설계가 완료되었습니다.");
    }

    private static void processCoverage(BufferedReader objReader, Coverage coverage) throws IOException, InvalidCostEnterException {
        boolean validCostInput = false;
        while (!validCostInput) {
            try {
                System.out.println("  *최고 보상기준 금액은 전체 근로자의 임금 평균액의 1.8배입니다(「산업재해보상보험법」 제36조제7항 본문).");
                String input = objReader.readLine().trim();
                int cost = Integer.parseInt(input);
                if (cost < 0) {
                    throw new InvalidCostEnterException();
                }
                coverage.setCoverageCost(cost);
                validCostInput = true;
            } catch (NumberFormatException e) {
                throw new InvalidCostEnterException("잘못된 입력입니다. 최대 보장 금액은 수치로 입력해주세요.");
            } catch (InvalidCostEnterException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private static void approveInsurance(BufferedReader objReader) throws IOException {
        ArrayList<Insurance> insurances = insuranceDao.retrieveNoApprove().getInsuranceList();
        System.out.println("***** 보험 목록 *****");
        for (Insurance insurance : insurances) {
            System.out.println(insurance.getInsuranceID() + " " + insurance.getInsuranceName());
        }

        System.out.println("상품 인가를 진행할 보험 ID를 입력하세요.");
        Insurance insurance = insuranceList.search(Integer.parseInt(objReader.readLine()));
        while (insurance == null) {
            System.out.println("존재하지 않는 보험ID를 입력하셨습니다. 다시 입력하세요.");
            insurance = insuranceList.search(Integer.parseInt(objReader.readLine()));
        }

        System.out.println("===== 보험 정보 =====");
        System.out.println("보험 ID : " + insurance.getInsuranceID());
        System.out.println("보험명 : " + insurance.getInsuranceName());
        System.out.println("보험유형 : " + insurance.getInsuranceType());
        System.out.println("보험 기본 가입비 : " + insurance.getInsuranceCost());
        System.out.println("보험내용 : " + insurance.getContents());
        System.out.println("상 - 보장내용 : " + insurance.getM_hcoverage().getCoverageContent());
        System.out.println("상 - 최대보장금액 : " + insurance.getM_hcoverage().getCoverageCost());
        System.out.println("중 - 보장내용 : " + insurance.getM_mcoverage().getCoverageContent());
        System.out.println("중 - 최대보장금액 : " + insurance.getM_mcoverage().getCoverageCost());
        System.out.println("하 - 보장내용 : " + insurance.getM_lcoverage().getCoverageContent());
        System.out.println("하 - 최대보장금액 : " + insurance.getM_lcoverage().getCoverageCost());
        System.out.println("상품 인가를 승인하시려면 [1], 거절하시려면 [2]를 입력하세요");

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date time = new Date();
        Approve approve = new Approve();

        switch (Integer.parseInt(objReader.readLine())) {
            case 1:
                approve.setInsuranceID(insurance.getInsuranceID());
                approve.setApproved(1);
                approve.setPermissionDate(format.format(time));
                System.out.println(format.format(time) + " 일자에 " + insurance.getInsuranceName() + " 보험이 인가 승인되었습니다.");
                insurance.setM_approve(approve);
                insuranceList.delete(insurance.getInsuranceID());
                approveDao.create(approve);
                break;
            case 2:
                System.out.println("상품 인가를 거절하셨습니다.");
                approve.setInsuranceID(insurance.getInsuranceID());
                approve.setApproved(0);
                approve.setPermissionDate(format.format(time));
                System.out.println("거절 사유를 입력해주세요.");
                approve.setPermissionRefuse(objReader.readLine());
                System.out.println("보험의 문제점을 입력해주세요.");
                approve.setInsuranceProblem(objReader.readLine());
                System.out.println(format.format(time) + "에 " + insurance.getInsuranceName() + "보험 인가를 거부하셨습니다.");
                insurance.setM_approve(approve);
                insuranceList.delete(insurance.getInsuranceID());
                approveDao.create(approve);
                break;
        }
    }

    private static void manageInsurance(BufferedReader objReader) throws IOException {
        System.out.println("===== 보험 목록 ======");
        InsuranceList insuranceList = insuranceDao.retrieveApprove();
        ArrayList<Insurance> insurances = insuranceList.getInsuranceList();
        for (Insurance insurance : insurances) {
            System.out.println(insurance.getInsuranceID() + " " + insurance.getInsuranceName());
        }
        System.out.println("판매실적표를 작성할 보험의 ID를 입력하세요.");
        int insuranceId;
        Insurance insurance = null;
        while (insurance == null) {
            try {
                insuranceId = readInsuranceId(objReader);
                insurance = insuranceList.search(insuranceId);
                if (insurance == null) System.out.println("존재하지 않는 보험 ID를 입력하셨습니다. 다시 입력하세요.");
            } catch (InvalidInsuranceIdException e) {
                System.out.println(e.getMessage());
                System.out.println("유효한(숫자 형태 입력 가능) 보험 ID를 입력하세요.");
            }
        }

        System.out.println("----- 보험정보 -----");
        System.out.println("보험 ID : " + insurance.getInsuranceID());
        System.out.println("보험명 : " + insurance.getInsuranceName());
        System.out.println("보험유형 : " + insurance.getInsuranceType());
        System.out.println("보험 기본 가입비 : " + insurance.getInsuranceCost());
        System.out.println("보험내용 : " + insurance.getContents());
        System.out.println("상 - 보장내용 : " + insurance.getM_hcoverage().getCoverageContent());
        System.out.println("상 - 최대보장금액 : " + insurance.getM_hcoverage().getCoverageCost());
        System.out.println("중 - 보장내용 : " + insurance.getM_mcoverage().getCoverageContent());
        System.out.println("중 - 최대보장금액 : " + insurance.getM_mcoverage().getCoverageCost());
        System.out.println("하 - 보장내용 : " + insurance.getM_lcoverage().getCoverageContent());
        System.out.println("하 - 최대보장금액 : " + insurance.getM_lcoverage().getCoverageCost());
        System.out.println("판매 실적표 작성을 진행하시려면 [1], 취소하시려면 [2]를 입력하세요");

        switch (Integer.parseInt(objReader.readLine())) {
            case 1:
                if (insurance.getM_SaleRecord().getInsuranceID() != 0) {
                    System.out.println("이미 기록된 판매실적표가 있습니다.");
                    System.out.println("--------- 판매실적표 ---------");
                    System.out.println("목표 개수 : " + insurance.getM_SaleRecord().getGoalCnt());
                    System.out.println("달성 개수 : " + insurance.getM_SaleRecord().getSaleCnt());
                    System.out.println("달성율 : " + ((double) insurance.getM_SaleRecord().getSaleCnt()
                            / (double) insurance.getM_SaleRecord().getGoalCnt()) * 100);
                } else {
                    SaleRecord saleRecord = new SaleRecord();
                    System.out.println("해당 보험의 목표 개수를 입력하세요.");
                    saleRecord.setGoalCnt(Integer.parseInt(objReader.readLine()));
                    System.out.println("해당 보험의 판매 개수를 입력하세요.");
                    saleRecord.setSaleCnt(Integer.parseInt(objReader.readLine()));
                    System.out.println("입력한 판매실적표는 다음과 같습니다. 맞으면 [1], 틀리면 [2]를 입력하세요.");
                    System.out.println("--------- 판매실적표 ---------");
                    System.out.println("목표 개수 : " + saleRecord.getGoalCnt());
                    System.out.println("달성 개수 : " + saleRecord.getSaleCnt());
                    System.out.println("달성율 : " + ((double) saleRecord.getSaleCnt() / (double) saleRecord.getGoalCnt()) * 100);

                    if (Integer.parseInt(objReader.readLine()) == 1) {
                        saleRecord.setInsuranceID(insurance.getInsuranceID());
                        saleRecordDao.create(saleRecord);
                        insurance.setM_SaleRecord(saleRecord);
                        System.out.println("판매실적표 작성이 완료되었습니다.");
                    }
                }
                break;
            case 2:
                System.out.println("판매실적표 작성이 취소되었습니다.");
                break;
        }
    }

    private static int readInsuranceId(BufferedReader objReader) throws IOException, InvalidInsuranceIdException {
        try {
            return Integer.parseInt(objReader.readLine());
        } catch (NumberFormatException e) {
            throw new InvalidInsuranceIdException("유효하지 않은 보험 ID입니다.");
        }
    }

    private static void manageInsurancepremium(BufferedReader objReader) throws IOException {
        System.out.println("======= 고객 관리 페이지에 진입했습니다. =======");
        System.out.println("고객 관리 유형 중 원하는 유형을 선택해주세요.");
        System.out.println("1. 보험료 관리");
        System.out.println("2. 보험료 미납 고객 관리하기");

        try {
            String managepreium = objReader.readLine().trim();
            switch (managepreium) {
                case "1":
                    System.out.println("===== 보험료 관리 페이지에 진입했습니다. =====");
                    System.out.println("보험료 관리 :: 고객 목록 조회");
                    System.out.println("고객명 | 보험료 납부 여부 | 가입 상품명");
                    manageInsurancepremiumManage(objReader);
                    break;
                case "2":
                    System.out.println("===== 보험료 미납 고객 관리화면 페이지 ======");
                    System.out.println("고객명 | 보험료 납부 여부(미납 횟수) | 블랙리스트 여부");
                    manageArrearsCustomer(objReader);
                    break;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static void manageInsurancepremiumManage(BufferedReader objReader) {
        try {
            refreshDBData("customer");
            refreshDBData("contract");

            ArrayList<Customer> lstCustomer = customerList.getCustomerList();
            for (Customer customer : lstCustomer) {
                System.out.print(customer.getCustomerName() + " | ");
                System.out.print(customer.getManageArrears() ? "N" : "Y");
                ArrayList<Contract> contracts = contractList.searchByCustomer(customer.getCustomerID());
                for (Contract contract : contracts) {
                    System.out.println(" | " + contract.getInsurance().getInsuranceName());
                }
            }

            System.out.println("보험료 납부일 공지할 고객명을 입력해주세요");
            String searchCustomerName = objReader.readLine().trim();
            Customer customerInfo = customerList.search(searchCustomerName);
            if (customerInfo != null) {
                if (customerInfo.getManageArrears()) {
                    refreshDBData("customer");
                    System.out.println("[" + customerInfo.getCustomerName() + "]고객에게 보험료 납부일 관련 문자 및 이메일을 전송하시겠습니까?");
                    System.out.println("1. 네    2. 아니오");
                    String sendEmail = objReader.readLine().trim();
                    switch (sendEmail) {
                        case "1":
                            boolean boolSendNotification = false;
                            if (customerInfo.geteMail() != null && !"".equals(customerInfo.geteMail())) {
                                boolSendNotification = true;
                                System.out.println("정상적으로 알림을 전송하였습니다!");
                            } else {
                                boolSendNotification = true;
                                try {
                                    throw new EmailNotAvailableException("이메일 정보가 없습니다!");
                                } catch (EmailNotAvailableException e) {
                                    e.sendNotification();
                                }
                            }
                            if (!boolSendNotification) {
                                System.out.println("시스템 오류로 인해 전송이 지연되고 있습니다 잠시만 기다려주세요!");
                                timer(5);
                                System.out.print("\n");
                                manageInsurancepremiumManage(objReader);
                            }
                            System.out.println("1. 닫기    2. 보험료 미납 고객 관리 페이지로 이동");
                            String closePopup = objReader.readLine().trim();
                            if (closePopup.equals("2")) manageArrearsCustomer(objReader);
                            break;
                        case "2":
                            System.out.println("해당 고객의 보험료 관리가 정상적으로 취소되었습니다! 재진행을 원하신다면 처음부터 진행해주세요!");
                            manageInsurancepremium(objReader);
                    }
                } else {
                    customerInfo = customerList.search(searchCustomerName);
                    if (!customerInfo.getManageArrears()) {
                        System.out.println("해당 고객은 보험료를 납부하지 않은 고객입니다! 고객 정보를 수정해주세요!");
                        System.out.println("1. 수정하기");
                        String modCustomer = objReader.readLine().trim();
                        if ("1".equals(modCustomer)) {
                            System.out.println("고객명 | 보험료 납부 여부(미납 횟수) | 블랙리스트 여부");
                            System.out.println(customerInfo.getCustomerName() + " | " + (customerInfo.getManageArrears() ? "N" : "Y") +
                                    "(" + customerInfo.getArrearsCount() + ") | " + (customerInfo.getBlackList() ? "Y" : "N"));
                            System.out.println("보험료 납부 여부 변경 (Y/N)");
                            String strManageArrears = objReader.readLine().trim();
                            if ("Y".equals(strManageArrears) || "N".equals(strManageArrears)) {
                                customerInfo.setManageArrears("N".equals(strManageArrears));
                                customerDao.updateID(customerInfo.getCustomerID(), customerInfo);
                                System.out.println("성공적으로 고객 정보를 수정하였습니다!");
                                manageInsurancepremiumManage(objReader);
                            }
                        }
                    }
                }
            } else {
                System.out.println("일치하는 고객이 없습니다");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static void manageArrearsCustomer(BufferedReader objReader) {
        try {
            refreshDBData("customer");
            ArrayList<Customer> lstCustomer = customerList.getCustomerList();
            for (int i = 0; i < lstCustomer.size(); i++) {
                Customer rowCustomer = lstCustomer.get(i);
                if (rowCustomer.getManageArrears()) {
                    String strCustomerInfo = new StringBuilder()
                            .append(rowCustomer.getCustomerName())
                            .append(" | " + "N(" + rowCustomer.getArrearsCount() + ")")
                            .append(" | " + (rowCustomer.getBlackList() ? "Y" : "N"))
                            .toString();
                    System.out.println(strCustomerInfo);
                }
            }
            System.out.println("정보를 조회할 고객명을 입력해주세요");
            String searchCustomerName = objReader.readLine().trim();
            Customer customerInfo = customerList.search(searchCustomerName);
            if (customerInfo != null) {
                refreshDBData("customer");
                customerInfo = customerList.search(searchCustomerName);
                if (customerInfo.getArrearsCount() == 0) {
                    System.out.println("해당 고객은 보험료를 이미 납부한 고객입니다! 고객 정보를 수정해주세요!");
                    System.out.println("1. 수정하기");
                    String modCustomer = objReader.readLine().trim();
                    if ("1".equals(modCustomer)) {
                        System.out.println("고객명 | 보험료 납부 여부(미납 횟수) | 블랙리스트 여부");
                        System.out.println(customerInfo.getCustomerName() + " | " + (customerInfo.getManageArrears() ? "N" : "Y") +
                                "(" + customerInfo.getArrearsCount() + ") | " + (customerInfo.getBlackList() ? "Y" : "N"));
                        System.out.println("보험료 납부 여부 변경 (Y/N)");
                        String strManageArrears = objReader.readLine().trim();
                        if ("Y".equals(strManageArrears) || "N".equals(strManageArrears)) {
                            customerInfo.setManageArrears("N".equals(strManageArrears));
                            customerDao.updateID(customerInfo.getCustomerID(), customerInfo);
                            System.out.println("성공적으로 고객 정보를 수정하였습니다!");
                            manageArrearsCustomer(objReader);
                        }
                    }
                    return;
                }
                if (customerInfo.getArrearsCount() >= 3) {
                    System.out.println("보험료 미납 횟수가 3회째입니다. 블랙리스트로 등록하시겠습니까?(Y/N)");
                    String blackList = objReader.readLine().trim();
                    if ("Y".equals(blackList)) {
                        customerInfo.setBlackList(true);
                        customerDao.updateID(customerInfo.getCustomerID(), customerInfo);
                        System.out.println("해당 고객을 블랙리스트로 등록 완료 했습니다!");
                    }
                }
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date nowDate = new Date();
                ArrayList<Contract> lstContract = contractList.searchByCustomer(customerInfo.getCustomerID());
                ArrayList<Contract> lstOverContract = new ArrayList<>();
                if (lstContract.size() > 0) {
                    System.out.println("[" + customerInfo.getCustomerName() + "] 고객님 가입된 보험 상품 목록");
                    System.out.println("보험료 미납기간 | 보험 상품명");
                    StringBuilder sbContractInfo = new StringBuilder();
                    for (int i = 0; i < lstContract.size(); i++) {
                        Contract rowContract = lstContract.get(i);
                        Insurance rowInsurance = rowContract.getInsurance();
                        sbContractInfo
                                .append(rowContract.getDate())
                                .append(" | " + rowInsurance.getInsuranceName() + "\n");
                        long diffDays = getDiffdays(sdf.format(nowDate), rowContract.getDate(), "yyyy-MM-dd");
                        if (diffDays > 60) lstOverContract.add(rowContract);
                    }
                    if (sbContractInfo != null) System.out.println(sbContractInfo.toString());
                    if (lstOverContract.size() > 0) {
                        System.out.println("1. 보험료 납입 최고 기간");
                        String strInsuranceMax = objReader.readLine().trim();
                        switch (strInsuranceMax) {
                            case "1":
                                System.out.println("미납 시작일 | 오늘 날짜");
                                System.out.println(lstOverContract.get(0).getDate() + " | " + sdf.format(nowDate));
                                System.out.println("1. 문자 발송");
                                String sendNotification = objReader.readLine().trim();
                                if ("1".equals(sendNotification)) {
                                    System.out.println("해당 고객에게 ‘보험료 납입 최고 기간’에 대한 서류를 발송하시겠습니까?");
                                    System.out.println("1. 네    2. 아니오");
                                    String sendNotificationConfirm = objReader.readLine().trim();
                                    if ("1".equals(sendNotificationConfirm)) {
                                        System.out.println("정상적으로 처리되었습니다!");
                                        System.out.println("해당 고객은 보험료 납입 최고 기간을 넘긴 고객입니다. 블랙리스트로 등록하시겠습니까?(Y/N)");
                                        String blackList = objReader.readLine().trim();
                                        if ("Y".equals(blackList)) {
                                            customerInfo.setBlackList(true);
                                            customerDao.updateID(customerInfo.getCustomerID(), customerInfo);
                                            System.out.println("해당 고객을 블랙리스트로 등록 완료 했습니다!");
                                        }
                                    }
                                }
                                break;
                        }
                    } else {
                        System.out.println("1. 미납 관련 알림");
                        String sendNotification = objReader.readLine().trim();
                        if ("1".equals(sendNotification)) {
                            System.out.println("1. 문자 전송하기    2. 더보기");
                            String sendNotification2 = objReader.readLine().trim();
                            if ("1".equals(sendNotification2)) {
                                boolean boolSendMessage = false;
                                boolSendMessage = true;
                                if (!boolSendMessage) {
                                    System.out.println("시스템 오류로 인해 문자가 정상적으로 전송되지 않았습니다. 잠시만 기다려주세요!");
                                    timer(5);
                                    System.out.print("\n");
                                    manageArrearsCustomer(objReader);
                                }
                                System.out.println("상담 내용을 작성해주세요:");
                                String counseling = objReader.readLine().trim();
                                customerInfo.setConsultContext(counseling);
                                pCustomerDao.updateID(customerInfo.getPCustomerID(), customerInfo);
                                System.out.println("상담 내용이 정상적으로 저장되었습니다!: " + counseling);
                                System.out.println("1. 닫기");
                                String closePopup = objReader.readLine().trim();
                                if ("1".equals(closePopup)) {
                                    return;
                                }
                            } else if ("2".equals(sendNotification2)) {
                                System.out.println("===== 이메일 전송 =====");
                                System.out.println("고객에게 이메일을 발송하시겠습니까?");
                                System.out.println("1. 네  2. 아니오");
                                String sendEmailConfirm = objReader.readLine().trim();
                                if ("1".equals(sendEmailConfirm)) {
                                    System.out.println("이메일 발송이 정상적으로 처리되었습니다!");
                                    System.out.println("상담 내용을 작성해주세요:");
                                    String counseling1 = objReader.readLine().trim();
                                    customerInfo.setConsultContext(counseling1);
                                    pCustomerDao.updateID(customerInfo.getPCustomerID(), customerInfo);
                                    System.out.println("상담 내용이 정상적으로 저장되었습니다: " + counseling1);
                                    System.out.println("1. 닫기");
                                    String closePopup = objReader.readLine().trim();
                                    if ("1".equals(closePopup)) {
                                        return;
                                    }
                                }
                            }
                        }
                    }
                } else {
                    System.out.println("해당 고객은 가입된 보험이 없습니다");
                }
            } else {
                System.out.println("일치하는 고객명이 없습니다");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static void acceptAccident(Customer bcustomer) throws IOException {
        Customer cusotmer = new Customer();
        Accident accident = new Accident();
        BufferedReader objReader = new BufferedReader(new InputStreamReader(System.in));

        if (bcustomer == null) {
            System.out.println("----------- 사고 접수하는 고객을 먼저 확인해주세요. 고객 정보 확인 메뉴로 이동합니다. ------------------");
            checkCustomerInfo(objReader);
        } else {
            accident.setCustomer(bcustomer);
            System.out.println();
            System.out.println("***** 사고정보를 입력해주세요. *****");
            System.out.println("사고 날짜(yyyy-mm-dd)를 입력해주세요.");
            accident.setAccidentDate(objReader.readLine().trim());
            if (accident.getAccidentDate().length() != 10) {
                System.out.println("정해진 양식에 맞게 입력하셔야 합니다. 사고 날짜(yyyy-mm-dd)");
                System.out.println("고객 정보부터 다시 확인해주세요.");
                checkCustomerInfo(objReader);
            }
            if (dateCalculate(accident.getAccidentDate())) {
                System.out.println("사고 시간을 입력해주세요.");
                accident.setAccidentTime(objReader.readLine().trim());
                System.out.println("사고 장소를 입력해주세요.");
                accident.setAccidentPlace(objReader.readLine().trim());
                System.out.println("사고 유형(building, car, driver)을 입력해주세요.");
                accident.setAccidentType(objReader.readLine().trim());
                System.out.println("사고 단계(상, 중, 하)를 입력해주세요.");
                accident.setAccidentSize(objReader.readLine().trim());
                if (accident.getAccidentDate() != "" && accident.getAccidentTime() != ""
                        && accident.getAccidentPlace() != "" && accident.getAccidentType() != null && accident.getAccidentSize() != null) {
                    System.out.println("사고 날짜: " + accident.getAccidentDate() + "\n"
                            + "사고 시간: " + accident.getAccidentTime() + "\n" +
                            "사고 장소 : " + accident.getAccidentPlace() + "\n"
                            + "사고 유형: " + accident.getAccidentType() + "\n" +
                            "사고단계 : " + accident.getAccidentSize());
                    System.out.println("사고 접수를 진행하시겠습니까? 1.네 2. 아니요");
                    int flagNum = Integer.parseInt(objReader.readLine().trim());
                    if (flagNum == 1) {
                        int accidentID = accidentDao.create(accident);
                        accident.setAccidentID(accidentID);
                        accident.setAccidentComplete(1);
                        accident.setJudgementComplete(1);
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                        Date time = new Date();
                        accident.setDate(format.format(time));
                        accidentDao.createInfo(accident);
                        accidentList.add(accident);
                        System.out.println("사고 접수가 정상적으로 처리되었습니다.");
                        System.out.println("1. 프로그램 종료");
                        String userInput = objReader.readLine().trim();
                        if (userInput.equals("1")) {
                            System.exit(0);
                        }
                    } else if (flagNum == 2) {
                        System.out.println("사고 접수를 취소하셨습니다. 고객 정보 확인 화면으로 다시 돌아갑니다.\n");
                    }
                } else {
                    System.out.println("미입력된 값이 존재합니다. 고객 정보 확인 화면으로 다시 돌아갑니다.\n");
                }
            } else System.out.println("약관에 따라 3년 이전에 발생된 사건의 경우 보험 접수가 불가합니다. 고객 정보 확인 화면으로 다시 돌아갑니다."); //A2 흐름
        }
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

    public static long getDiffdays(String firstDate, String secondDate, String dateFormat) {
        long diffDays = 0;
        try {
            if (dateFormat == null || "".equals(dateFormat)) {
                dateFormat = "yyyy-MM-dd";
            }
            if (firstDate != null && !"".equals(firstDate) && secondDate != null && !"".equals(secondDate)) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREAN);
                Date fDate = sdf.parse(firstDate);
                Date sDate = sdf.parse(secondDate);

                long diffMillisec = Math.abs(sDate.getTime() - fDate.getTime());
                diffDays = TimeUnit.DAYS.convert(diffMillisec, TimeUnit.MILLISECONDS);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return diffDays;
    }

    private static boolean dateCalculate(String accidentDate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String endDate = df.format(cal.getTime());
        cal.add(Calendar.YEAR, -3);
        String startDate = df.format(cal.getTime());
        LocalDate localDate = LocalDate.parse(accidentDate);
        LocalDate startLocalDate = LocalDate.parse(startDate);
        LocalDate endLocalDate = LocalDate.parse(endDate);
        endLocalDate = endLocalDate.plusDays(3);
        return (!localDate.isBefore(startLocalDate)) && (localDate.isBefore(endLocalDate));
    }

    private static void consult(BufferedReader objReader) {
        try {
            while (true) {
                int submenu = 0;
                ArrayList<PCustomer> customers = pCustomerList.getCustomerList();
                System.out.println("======= 잠재 고객 관리 =======");
                System.out.println("잠재 고객을 관리하시겠습니까? 1-예 2-나가기");
                submenu = Integer.parseInt(objReader.readLine());
                if (customers.isEmpty()) {
                    scException.customerEmptyException(objReader);
                } else {
                    if (submenu == 2) {
                        break;
                    }
                    System.out.println("관리할 잠재 고객의 ID를 선택하세요.");
                    if (customers == null) {
                        System.out.println("죄송합니다! 현재 시스템에 오류가 발생했습니다. 기술팀에 문의 주시거나 다시 시도해주시기 바랍니다. 1-확인");
                        Integer.parseInt(objReader.readLine());
                    } else {
                        for (PCustomer customer : customers) {
                            System.out.println(customer.getPCustomerID() + " " + customer.getCustomerName() + " " + customer.getPhoneNumber() + " " + customer.getDate());
                        }
                        int selectionPCustomer = 0;
                        while (true) {
                            selectionPCustomer = Integer.parseInt(objReader.readLine());
                            System.out.println("1. 상담 진행하기");
                            System.out.println("2. 관리 대상 변경");
                            submenu = Integer.parseInt(objReader.readLine());
                            if (submenu == 1) {
                                break;
                            } else {
                                System.out.println("상담할 고객 아이디 재입력을 진행합니다.");
                            }
                        }

                        PCustomer pCustomer = pCustomerList.search(selectionPCustomer);
                        System.out.println(pCustomer.getCustomerName() + " " + pCustomer.getPhoneNumber() + " " + "현재 상담 진행중.");
                        System.out.println("1. 상담 종료");
                        System.out.println("2. 고객 부재");
                        submenu = Integer.parseInt(objReader.readLine());
                        switch (submenu) {
                            case 1:
                                System.out.println("상담 이력을 저장합니다.");
                                Customer customer = new Customer();
                                customer.setPCustomerID(pCustomer.getPCustomerID());
                                customer.setCustomerName(pCustomer.getCustomerName());
                                customer.setDate(pCustomer.getDate());
                                customer.setPhoneNumber(pCustomer.getPhoneNumber());
                                while (true) {
                                    System.out.println("고객의 주민번호를 입력하세요.");
                                    customer.setCustomerNumber(objReader.readLine());
                                    System.out.println("고객의 성별을 입력하세요.");
                                    customer.setSex(objReader.readLine());
                                    System.out.println("고객의 직업을 입력하세요.");
                                    customer.setJob(objReader.readLine());
                                    System.out.println("고객의 주소를 입력하세요.");
                                    customer.setAddress(objReader.readLine());
                                    System.out.println("상담 이력을 입력하세요.");
                                    customer.setConsultContext(objReader.readLine());
                                    System.out.println("저장하시겠습니까? 1-예 2-아니오");
                                    submenu = Integer.parseInt(objReader.readLine());
                                    if (submenu == 1) {
                                        Customer customercheck = customerList.searchByPID(customer.getPCustomerID());
                                        if (customercheck == null) {
                                            boolean check = customerDao.createManagePcustomer(customer);
                                            if (check) {
                                                setDBData();
                                                System.out.println("상담 내용을 정상적으로 저장하였습니다!");
                                            } else {
                                                System.out.println("죄송합니다! 현재 시스템에 오류가 발생했습니다. 기술팀에 문의 주시거나 다시 시도해주시기 바랍니다. 입력된 상담 이력을 파일로 저장하시겠습니까?\n1-에 2-아니오");
                                                submenu = Integer.parseInt(objReader.readLine());
                                                if (submenu == 1) {
                                                    System.out.println("상담 이력을 파일로 저장했습니다.");
                                                } else if (submenu == 2) {
                                                    break;
                                                }
                                            }
                                        } else {
                                            System.out.println("이미 관리가 완료된 고객입니다!");
                                        }
                                        break;
                                    } else if (submenu == 2) {
                                        System.out.println("상담 내용 재입력을 진행합니다.");
                                    }
                                }
                                break;
                            case 2:
                                System.out.println("고객이 부재중입니다.");
                                System.out.println("작업중인 직원의 이름을 입력하세요.");
                                String employeeName = objReader.readLine();
                                System.out.println("안녕하세요. 신동아화재 직원 " + employeeName + "입니다. " +
                                        "고객님께 꼭 필요하실 보험 상품이 출시되어 안내차 연락 드립니다. 상담을 원하실 경우 1588-3333으로 연락 부탁드립니다.");
                                System.out.println("알림을 전송하시겠습니까? 1-예 2-아니오");
                                submenu = Integer.parseInt(objReader.readLine());
                                if (submenu == 1) {
                                    System.out.println("알림이 성공적으로 전송되었습니다!");
                                    break;
                                }
                        }
                    }
                }
            }
        } catch (IOException e) {
//                 e.printStackTrace();
        } catch (NullPointerException e) {
            scException.customerEmptyException(objReader);
        } catch (NumberFormatException e) {
            scException.nullMenuException();
//                   e.printStackTrace();
        }
    }

    private static void manageCustomer(BufferedReader objReader) {
        while (true) {
            try {
                int submenu = 0;
                ArrayList<Customer> customers = customerList.getCustomerList();
                System.out.println("======= 고객 관리 =======");
                System.out.println("고객 정보를 수정하시겠습니까? 1-예 2-나가기");
                submenu = Integer.parseInt(objReader.readLine());
                if (submenu == 2) {
                    break;
                }
                System.out.println("관리할 고객의 이름을 선택하세요.");
                for (Customer customer : customers) {
                    ArrayList<Contract> contracts = contractList.searchByCustomer3(customer.getCustomerID());
                    System.out.println("---" + customer.getCustomerName());
                    if (contracts == null) {
                        System.out.println("가입한 보험이 없습니다.");
                    } else {
                        for (Contract contract : contracts) {
                            System.out.println(contract.getInsurance().getInsuranceName() + " " + contract.getDate());
                        }
                    }
                }
                String customerName = objReader.readLine();
                int customerIndex = 0;
                for (Customer cus : customers) {
                    if (cus.getCustomerName().equals(customerName)) {
                        customerIndex = customers.indexOf(cus);
                        break;
                    }
                }
                Customer customer = customers.get(customerIndex);
                System.out.println(customer.getCustomerNumber() + " " + customer.getCustomerName() + " "
                        + customer.getSex() + " " + customer.getJob() + " " + customer.getPhoneNumber() + " "
                        + customer.geteMail() + " " + customer.getAddress() + " " + customer.getDate());
                ArrayList<Contract> contracts = contractList.searchByCustomer(customer.getCustomerID());
                if (contracts == null) {
                    System.out.println("가입한 보험이 없습니다.");
                } else {
                    for (Contract contract : contracts) {
                        System.out.println(contract.getInsurance().getInsuranceName());
                    }
                }
                System.out.println("1-수정 2-나가기");
                submenu = Integer.parseInt(objReader.readLine());
                switch (submenu) {
                    case 1:
                        while (true) {
                            Customer customertemp = new Customer();
                            System.out.println("수정 정보를 입력합니다.");
                            System.out.println("고객의 이름을 입력하세요.");
                            customertemp.setCustomerName(objReader.readLine());
                            System.out.println("고객의 직업을 입력하세요.");
                            customertemp.setJob(objReader.readLine());
                            System.out.println("고객의 전화번호를 입력하세요.");
                            customertemp.setPhoneNumber(objReader.readLine());
                            System.out.println("고객의 이메일을 입력하세요.");
                            customertemp.seteMail(objReader.readLine());
                            System.out.println("고객의 주소를 입력하세요.");
                            customertemp.setAddress(objReader.readLine());
                            System.out.println("1-저장 2-나가기");
                            submenu = Integer.parseInt(objReader.readLine());
                            if (submenu == 1) {
                                System.out.println(customertemp.getCustomerName() + " " + customertemp.getJob() + " "
                                        + customertemp.getPhoneNumber() + " " + customertemp.geteMail() + " "
                                        + customertemp.getAddress());
                                System.out.println("해당 정보가 맞습니까? 1-확인 2-취소");
                                submenu = Integer.parseInt(objReader.readLine());
                                if (submenu == 1) {
                                    customertemp.setPCustomerID(customer.getPCustomerID());
                                    customertemp.setCustomerID(customer.getCustomerID());
                                    boolean check = customerDao.update(customertemp);
                                    if (check) {
                                        setDBData();
                                        System.out.println("저장이 완료되었습니다.");
                                    } else {
                                        System.out.println("죄송합니다! 현재 시스템에 오류가 발생했습니다. 기술팀에 문의 주시거나 다시 시도해주시기 바랍니다. 입력된 수정 정보를 파일로 저장하시겠습니까?\n1-예 2-아니오");
                                        submenu = Integer.parseInt(objReader.readLine());
                                        if (submenu == 1) {
                                            System.out.println("수정 내용을 파일로 저장했습니다.");
                                        }
                                        break;
                                    }
                                    break;
                                } else if (submenu == 2) {
                                    System.out.println("수정 정보 입력 화면으로 돌아갑니다.");
                                }
                            } else if (submenu == 2) {
                                System.out.println("변경 사항이 저장되지 않습니다. 수정을 취소하고 나가시겠습니까?");
                                System.out.println("1-예 2-아니오");
                                submenu = Integer.parseInt(objReader.readLine());
                                if (submenu == 1) {
                                    break;
                                } else if (submenu == 2) {
                                    System.out.println("수정 정보 입력 화면으로 돌아갑니다.");
                                }
                            }
                        }
                        break;
                    case 2:
                        System.out.println("관리할 고객 이름 선택 화면으로 돌아갑니다.");
                        break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (NullPointerException e) {
                scException.customerEmptyException(objReader);
                break;
            } catch (NumberFormatException e) {
                scException.nullMenuException();
//                   e.printStackTrace();
            }
        }
    }

    private static void checkCustomerInfo(BufferedReader objReader) {
        try {
            int submenu = 0;
            while (true) {
                System.out.println("======= 고객 정보 확인 =======");
                System.out.println("사고 접수 고객의 정보를 확인하시겠습니까? 1-예 2-나가기");
                submenu = Integer.parseInt(objReader.readLine());
                if (submenu == 2) {
                    break;
                }
                Customer customer = new Customer();
                System.out.println("이름을 입력하세요.");
                customer.setCustomerName(objReader.readLine());
                System.out.println("성별을 입력하세요.");
                customer.setSex(objReader.readLine());
                System.out.println("주민번호를 입력하세요.");
                customer.setCustomerNumber(objReader.readLine());
                System.out.println("전화번호를 입력하세요.");
                customer.setPhoneNumber(objReader.readLine());

                if (!customer.getCustomerName().equals("")
                        && !customer.getCustomerNumber().equals("")
                        && !customer.getSex().equals("")
                        && !customer.getPhoneNumber().equals("")) {
                    System.out.println("이름 : " + customer.getCustomerName() + "\n" + "주민번호 : " + customer.getCustomerNumber());
                    System.out.println("사고 접수 된 고객 정보를 확인하겠습니까? 1-네");
                    submenu = Integer.parseInt(objReader.readLine());
                    if (submenu == 1) {
                        Customer bcustomer = customerList.search(customer);
                        if (bcustomer != null) {
                            ArrayList<Contract> contracts = contractList.searchByCustomer3(bcustomer.getCustomerID());
                            if (contracts.isEmpty()) {
                                System.out.println("보험상품에 가입되지 않은 고객입니다.");
                                System.out.println("1-확인");
                                Integer.parseInt(objReader.readLine());
                                break;
                            } else {
                                System.out.println("고객 이름 : " + bcustomer.getCustomerName());
                                System.out.println("고객 전화번호 : " + bcustomer.getPhoneNumber());
                                System.out.println("고객 직업 : " + bcustomer.getJob());
                                System.out.println("고객 주소 : " + bcustomer.getAddress());
                                System.out.println("고객 주민번호 : " + bcustomer.getCustomerNumber());
                                int i = 0;
                                for (Contract contract : contracts) {
                                    i++;
                                    System.out.println("-- 계약 정보" + i + " --");
                                    System.out.println("납입보험료 : " + contract.getPrice());
                                    System.out.println("만기일 : " + contract.getEndDate());
                                    try {
                                        Date today = new Date();
                                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                                        Date endDate = formatter.parse(contract.getEndDate());
                                        if (today.after(endDate)) {
                                            System.out.println("만기된 보험");
                                        }
                                    } catch (ParseException e) {
                                        System.out.println("날짜 입력에 문제가 있습니다. 기술팀에 문의 주시기 바랍니다.");
//                                 e.printStackTrace();
                                    }
                                }
                                System.out.println("1-사고접수하기 2-취소");
                                submenu = Integer.parseInt(objReader.readLine());
                                if (submenu == 1) {
                                    System.out.println("사고를 접수하는 페이지로 이동합니다.");
                                    acceptAccident(bcustomer);
                                }
                                if (submenu == 2) ;
                            }
                        } else {
                            System.out.println("없는 고객 정보입니다. 보상 메뉴 처음으로 돌아갑니다.");
                        }
                    }
                } else {
                    System.out.println("입력되지 않은 정보가 있습니다.");
                    System.out.println("1-확인");
                    Integer.parseInt(objReader.readLine());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            scException.nullMenuException();
//                   e.printStackTrace();
        } catch (NullPointerException e) {
            aaException.disconnectCustomerList(objReader);
        }

    }

    private static void manageContract(BufferedReader objReader) throws IOException {
        Contract contract = new Contract();
        ArrayList<Contract> contract3 = contractList.getContractList();
        Employee employee = new Employee();
        int flag = 0;
        System.out.println("------------계약 관리하기------------");
        for (Contract contract6 : contract3) {
            System.out.println("계약 날짜: " + contract6.getDate() + "  계약 ID: " + contract6.getContractID() + "  보험 이름: " + contract6.getInsurance().getInsuranceName() + "  고객 이름: " + contract6.getCustomer().getCustomerName());
        }
        System.out.println("관리하고자 하는 계약 ID를 입력하세요.");
        String scontractID = objReader.readLine().trim();
        int contractID = Integer.parseInt(scontractID);
        Contract contractm = new Contract();
        contract.setContractID(contractID);
        ArrayList<Contract> contractfind = contractList.searchByContractID(contractID);

        while (true) {
            for (Contract contract4 : contractfind) {
                if (contract4.getContractID() == contractID) {
                    contract = contract4;
                    contract.setContractID(contractID);
                    System.out.println("계약 날짜: " + contract4.getDate() + "   보험명: " + contract4.getInsurance().getInsuranceName() + "   고객 이름: " + contract4.getCustomer().getCustomerName() + "   전화번호: " + contract4.getCustomer().getCustomerNumber() + "   납입 보험료 :" + contract4.getPrice() + "   만기일: " + contract4.getEndDate());
                } else {
                    System.out.println("입력하신 보험ID가 리스트에 없습니다. 다시 입력하세요.");
                    break;
                }
            }
            System.out.println("1. 신규 상품 체결, 2. 연장");
            String button = objReader.readLine().trim();
            if (button.equals("1")) {
                contract(objReader);
                break;
            } else if (button.equals("2")) {
                if (flag == 1) contract.getInsurance().setContents("건물보험");
                if (contract.getInsurance().getContents() == null) {
                    noExtend.noExtendComplete(objReader);
                    flag = 1;
                    continue;
                }
                while (true) {
                    System.out.println("연장 가능한 갱신연도(1년 연장, 2년 연장, 3년 연장)를 입력하세요.");
                    String syear = objReader.readLine().trim();
                    if (syear.equals("1") || syear.equals("2") || syear.equals("3")) {
                        int finalEndD = 0;
                        Calendar cal = null;
                        String newEndDate = contract.getEndDate();
                        try {
                            int year = Integer.parseInt(syear);
                            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                            cal = Calendar.getInstance();
                            Date date = dateFormat.parse(contract.getEndDate());
                            cal.setTime(date);
                            cal.add(Calendar.YEAR, year);
                            newEndDate = dateFormat.format(cal.getTime());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        contract.setEndDate(newEndDate);
                        contractDao.updateID(contract.getContractID(), contract);
                        contractList.delete(contract.getContractID());
                        contractList.add(contract);
                        System.out.println("연장된 만기일 : " + new Date(cal.getTimeInMillis()));
                        break;
                    } else {
                        manageContractException.NotValuableExtend(objReader);
                        continue;
                    }
                }
            }
            break;
        }
    }

    private static void contract(BufferedReader objReader) throws IOException {
        System.out.println("------------계약 체결하기------------");
        Insurance insurance = new Insurance();
        Contract contract2 = new Contract();
        ArrayList<Customer> customers = customerList.getCustomerList();
        Customer customer = new Customer();
        Contract contract = new Contract();
        Employee employee = new Employee();
        int count = 0;
        ArrayList<Insurance> insuranceLists = insuranceList.getInsuranceList();
        ArrayList<Contract> contractLists = contractList.getContractList();
        int flag = 0;
        Contract contractn = new Contract();
        Customer c = new Customer();
        Customer customerc = customerList.searchConsult(customer);
        while (true) {
            if (flag == 1)
                break;
            for (Customer cu : customers) {
                if (cu.getConsultContext().equals("")) {
                    System.out.println("상담 날짜: " + cu.getDate() + " 이름: " + cu.getCustomerName());
                    count++;
                    if (count == customerList.getCustomerList().size()) {
                        flag = 1;
                        break;
                    }
                } else {
                    System.out.println("상담 날짜: " + cu.getDate() + " 이름: " + cu.getCustomerName());
                }
            }
            if (flag == 0) {
                System.out.println("이름을 입력하세요.");
                String customername = objReader.readLine().trim();
                if (customerList.search(customername) == null) {
                    contractException.NoNameList(objReader);
                    continue;
                } else {
                    customer.setCustomerName(customername);
                    customer.setCustomerName(customername);
                    int customerID = customer.getCustomerID();
                    customer = customerList.search(customername);
                    contract.setCustomer(customer);
                    insurance.setContract(contract);

                    while (true) {
                        System.out.println("이름: " + customer.getCustomerName() + "  주민번호: " + customer.getCustomerNumber() +
                                "  성별: " + customer.getSex() + "  주소: " + customer.getAddress() + "  현재 직업: " + customer.getJob() +
                                "  상담 날짜: " + customer.getDate() + "  상담 내용:" + customer.getConsultContext());
                        for (Insurance in : insuranceLists) {
                            in.setContract(contract);
                            System.out.println("보험 ID: " + in.getInsuranceID() + "  보험 이름: " + in.getInsuranceName() + " 보험료: " + in.getInsuranceCost());
                        }
                        System.out.println("보험종류(ID), 보험 이름을 선택하고 만기일, 보험료를 입력하세요.");
                        System.out.println("보험ID를 입력하세요.");
                        final String REGEX = "[0-9]+";
                        String sinsuranceID = objReader.readLine().trim();
                        int iinsuranceID = 0;
                        int insuranceID = 0;
                        if (sinsuranceID.equals("")) {
                            insuranceID = 0;
                        } else {
                            iinsuranceID = Integer.parseInt(sinsuranceID);
                            insuranceID = iinsuranceID;
                        }

                        System.out.println("보험이름을 입력하세요.");
                        String insuranceName = objReader.readLine().trim();
                        System.out.println("계약날짜를 입력하세요.");
                        String insuranceDate = objReader.readLine().trim();
                        System.out.println("만기일을 입력하세요.");
                        String insuranceEndDate = objReader.readLine().trim();
                        System.out.println("보험료를 입력하세요.");
                        String sinsuranceCost = objReader.readLine().trim();
                        int iinsuranceCost = 0;
                        int insuranceCost = 0;
                        if (sinsuranceCost.equals("")) {
                            insuranceCost = 0;
                        } else {
                            iinsuranceCost = Integer.parseInt(sinsuranceCost);
                            insuranceCost = iinsuranceCost;
                        }
                        if (sinsuranceID.equals("") || insuranceName.equals("") || insuranceEndDate.equals("") || sinsuranceCost.equals("")) {
                            System.out.println("입력되지 않은 정보가 있습니다.");
                            continue;
                        } else {
                            System.out.println("해당 보험 계약을 체결하시겠습니까?");
                            System.out.println("1. 예 2. 보류");
                            String button = objReader.readLine().trim();
                            if (button.equals("1")) {
                                insurance.setInsuranceID(insuranceID);
                                contract = new Contract();

                                contract.setInsurance(insurance);
                                contract.getInsurance().setInsuranceID(insuranceID);
                                contract.getInsurance().setInsuranceName(insuranceName);
                                contract.setDate(insuranceDate);
                                contract.setEndDate(insuranceEndDate);
                                contract.setPrice(insuranceCost);
                                contract.setCustomer(customer);
                                employee.setName("김나물");
                                contract.setEmployee(employee);
                                if (contract.getCustomer().geteMail() == null) {
                                    contractException.NotSendEmail(objReader);
                                    flag = 1;
                                    break;
                                }
                                customer.seteMail("직원 이름: " + contract.getEmployee().getName() + " " + "보험 종류: " + contract.getContractID() + " " + "보험 이름: " + contract.getInsurance().getInsuranceName() + " " + "만기일: " + contract.getEndDate() + " " + "보험료: " + contract.getPrice());//이메일 전송
                                int contractID = contractDao.create(contract);
                                contract.setContractID(contractID);
                                System.out.println("이메일을 전송을 완료하였습니다.");
                                flag = 1;
                                break;
                            } else if (button.equals("2")) {
                                System.out.println("보류하였습니다.");
                                continue;
                            }
                        }
                    }
                }
            } else {
                System.out.println("현재 보험 체결을 원하는 고객이 없습니다.");
                break;
            }
        }
    }


    private static void accidentnvestigation(BufferedReader objReader) throws NumberFormatException, IOException {
        System.out.println("-------------------사고 상황 조사하기---------------------");
        Accident accident = new Accident();
        ArrayList<Accident> accidents = accidentList.getAccidentList();
        ArrayList<Customer> customers = customerList.getCustomerList();
        Customer customer = new Customer();
        SiteInfo siteInfo = new SiteInfo();
        int flag = 0;

        while (true) {
            for (Accident ac : accidents) {
                System.out.println("사고번호: " + ac.getAccidentID() + " 사고일자: " + ac.getAccidentDate() + " 사고시각: " +
                        ac.getAccidentTime() + " 사고위치: " + ac.getAccidentPlace() + " 사고위험정도(고, 중, 저): " + ac.getAccidentSize() +
                        " 처리유무(0.처리, 1.미처리)): " + ac.getAccidentComplete());
            }

            if (flag == 1) {
                String sID = objReader.readLine().trim();
                int ID = Integer.parseInt(sID);
                Accident aaccident = accidentList.search(ID);
                ArrayList<Accident> noRetrieveAccidents = accidentDao.retrieveNotcompleted().getAccidentList();
                for (Accident ac : noRetrieveAccidents) {
                    ac = aaccident;
                    System.out.println("사고번호: " + ac.getAccidentID() + " 사고일자: " + ac.getAccidentDate() + " 사고시각: " + ac.getAccidentTime() +
                            " 사고위치: " + ac.getAccidentPlace() + " 사고위험정도(고, 중, 저): " + ac.getAccidentSize() + " 처리유무(0.처리, 1.미처리)): " + ac.getAccidentComplete());
                    break;
                }
                System.out.println("해당 사고에 대해 면/부책 심사를 신청하겠습니까?");
                System.out.println("1.확인");
                String select = objReader.readLine().trim();
                decideExemption(objReader);
                break;
            }
            System.out.println("미처리번호(1) 입력 후 사고번호를 입력하거나 2.사고 접수 직접하기를 클릭하세요.");
            String selects = objReader.readLine().trim();
            int select = Integer.parseInt(selects);
            if (select == 1) {
                String sID = objReader.readLine().trim();
                int ID = Integer.parseInt(sID);
                Accident aaccident = accidentList.search(ID);
                accident = aaccident;
                ArrayList<Accident> noRetrieveAccidents = accidentDao.retrieveNotcompleted().getAccidentList();
                int length = noRetrieveAccidents.size();
                for (Accident ac : noRetrieveAccidents) {
                    ac = accident;
                    System.out.println("사고번호: " + ac.getAccidentID() + " 사고일자: " + ac.getAccidentDate() + " 사고시각: " + ac.getAccidentTime() + " 사고위치: " + ac.getAccidentPlace() + " 사고위험정도(고, 중, 저): " + ac.getAccidentSize() + " 처리유무(0.처리, 1.미처리)): " + ac.getAccidentComplete());
                    break;
                }
                System.out.println("1. 현장정보추가하기");
                int okbutton = Integer.parseInt(objReader.readLine());
                System.out.println("현장 정보(사건내용, 사건 녹취록, 사진, 영상, 피해규모)를 입력하세요.");
                System.out.println("사건내용을 입력하세요.");
                String senario = objReader.readLine().trim();
                siteInfo.setScenario(senario);
                System.out.println("사건 녹취록(숫자)을 입력하세요.");
                int record = Integer.parseInt(objReader.readLine());
                siteInfo.setRecord(record);
                System.out.println("사진(숫자)을 입력하세요.");
                int photo = Integer.parseInt(objReader.readLine());
                siteInfo.setPhoto(photo);
                System.out.println("영상(숫자)을 입력하세요.");
                int video = Integer.parseInt(objReader.readLine());
                siteInfo.setVideo(video);
                System.out.println("피해규모를 입력하세요.");
                String damageScale = objReader.readLine().trim();
                siteInfo.setDamageScale(damageScale);
                System.out.println("1. 파일 업로드");
                int upload = Integer.parseInt(objReader.readLine());
                accident.setM_siteInfo(siteInfo);
                int customerID = accident.getCustomer().getCustomerID();
                Customer ncustomer = customerList.search(customerID);
                accident.setCustomer(ncustomer);
                String customerName = ncustomer.getCustomerName();
                if (ncustomer.geteMail().equals("")) {
                    investigationException.NoFileUpload(objReader);
                    break;
                }
                System.out.println("시스템은 해당 사고를 시스템에 등록하겠습니까?");
                System.out.println("1. 확인");
                int register = Integer.parseInt(objReader.readLine());
                accident.setAccidentComplete(0);
                accidentDao.updateInvestigation(accident);
                accidentDao.update(accident);
                accidentDao.updateAccidentComplete(accident);
                accidentList.add(accident);
                if (register == 1) {
                    accident.setAccidentComplete(0);
                    flag = 1;
                    continue;
                }
                System.out.println("해당 사고에 대해 면/부책 심사를 신청하겠습니까?");
                System.out.println("1.확인");
                int okay = Integer.parseInt(objReader.readLine());
                decideExemption(objReader);
            } else {
                System.out.println("사고 접수와 관련된 사항((사고일자, 사고시각, 사고위치, 사고위험정도(고 , 중, 저), 처리유무, 현장 정보(사건내용, 사건녹취록, 사진, 영상, 피해규모) 입력할 수 있는 입력창");
                String accidentTime = objReader.readLine().trim();
                accident.setAccidentTime(accidentTime);
                String date = objReader.readLine().trim();
                accident.setAccidentDate(date);
                String accidentPlace = objReader.readLine().trim();
                accident.setAccidentPlace(accidentPlace);
                String accidentSize = objReader.readLine().trim();
                accident.setAccidentSize(accidentSize);
                int accidentComplete = Integer.parseInt(objReader.readLine());
                accident.setAccidentComplete(accidentComplete);
                String scenario = objReader.readLine().trim();
                siteInfo.setScenario(scenario);
                int record = Integer.parseInt(objReader.readLine());
                siteInfo.setRecord(record);
                int photo = Integer.parseInt(objReader.readLine());
                siteInfo.setPhoto(photo);
                int video = Integer.parseInt(objReader.readLine());
                siteInfo.setVideo(video);
                String damageScale = objReader.readLine().trim();
                siteInfo.setDamageScale(damageScale);
                System.out.println("1.업로드하기");
                String upload = objReader.readLine().trim();
                System.out.println("보험사 시스템 내에 등록하시겠습니까?");
                System.out.println("1.확인");
                String okay = objReader.readLine().trim();
                int customersize = customerList.getCustomerList().size();
                accident = new Accident();
                accident.setCustomer(customer);
                accident.setAccidentComplete(0);
                accident.setM_siteInfo(siteInfo);
                accident.setAccidentComplete(0);
                accident.setJudgementComplete(1);
                accident.setAccidentTitle(accident.getM_siteInfo().getScenario());
                accident.setDate(accident.getAccidentDate());
                accident.setAccidentType(accident.getM_siteInfo().getScenario());
                accidentDao.create(accident);
                accidentDao.update(accident);
                accidentDao.createInvestigation(accident);
                accidentList.add(accident);
                System.out.println(accident.getAccidentTime());

                ArrayList<Accident> RetrieveAccidents = accidentDao.retrievecommpleted().getAccidentList();
                for (Accident ac : RetrieveAccidents) {
                    ac = accident;
                    accidentList.add(ac);
                    System.out.println("사고번호: " + ac.getAccidentID() + " 사고일자: " + ac.getAccidentDate() + " 사고시각: " +
                            ac.getAccidentTime() + " 사고위치: " + ac.getAccidentPlace() + " 사고위험정도(고, 중, 저): " + ac.getAccidentSize() +
                            " 처리유무(1. 처리, 2. 미처리)): " + ac.getAccidentComplete());
                    break;
                }
            }
            break;
        }
    }

    private static void decideExemption(BufferedReader objReader) throws NumberFormatException, IOException {
        AccidentDaoImpl accidentDao = new AccidentDaoImpl();
        AccidentList accidentList = accidentDao.retrieve();
        Exemption exemption = new Exemption();
        ExemptionList exemptionList = new ExemptionListImpl();
        ExemptionDaoImpl exemptionDao = new ExemptionDaoImpl();
        ArrayList<Accident> accidents = accidentDao.retrievecommpleted().getAccidentList();

        System.out.println("면/부책 심사하기");
        if (accidents != null) {
            for (Accident per_accident : accidents) {
                System.out.println("사고번호: " + per_accident.getAccidentID() + "\n" +
                        "사고접수일: " + per_accident.getDate() + "\n" +
                        "사고 위험도(고, 중, 저): " + per_accident.getAccidentSize() + "\n" +
                        "심사여부 (0 거절, 1 승인): " + per_accident.getJudgementComplete() + "\n");
                if (per_accident.getAccidentID() == 0) {
                    rewardException.NoLinkedList(objReader);
                } else if (per_accident.getDate() == null) {
                    rewardException.NoLinkedList(objReader);
                } else if (per_accident.getAccidentSize().equals("")) {
                    rewardException.NoLinkedList(objReader);
                } else if (per_accident.getJudgementComplete() == 2) {
                    rewardException.NoLinkedList(objReader);
                }
            }
        } else {
            rewardException.NoLinkedList(objReader);
        }
        int select = Integer.parseInt(objReader.readLine());
        Accident selectedAccident = accidentList.search(select);

        System.out.println(selectedAccident.getAccidentDate() + " " + selectedAccident.getAccidentTime() + " " + selectedAccident.getAccidentPlace()
                + " " + selectedAccident.getAccidentSize() + " " + selectedAccident.getM_siteInfo().getScenario() + " " + selectedAccident.getM_siteInfo().getRecord()
                + " " + selectedAccident.getM_siteInfo().getPhoto() + " " + selectedAccident.getM_siteInfo().getVideo() + " " + selectedAccident.getM_siteInfo().getDamageScale());

        while (true) {
            System.out.println("면/부책 여부:");
            String judgementComplete = objReader.readLine();
            if (judgementComplete.equals("")) {
                System.out.println("미입력정보가 존재합니다");
                continue;
            }
            selectedAccident.setJudgementComplete(Integer.parseInt(judgementComplete));
            System.out.println("판단 사유:");
            String reason = objReader.readLine();
            if (reason.equals("")) {
                System.out.println("미입력정보가 존재합니다");
                continue;
            }
            exemption.setReason(reason);
            System.out.println("참고자료:");
            String subFile = (objReader.readLine());
            if (subFile.equals("")) {
                System.out.println("미입력정보가 존재합니다");
                continue;
            }
            exemption.setSubFile(Integer.parseInt(subFile));
            System.out.println("관련법규:");
            String legacy = objReader.readLine();
            if (legacy.equals("")) {
                System.out.println("미입력정보가 존재합니다");
                continue;
            }
            exemption.setLegacy(legacy);
            exemptionList.add(exemption);

            System.out.println("손해조사 심사가 완료되었습니다");
            exemption.setAccidentID(selectedAccident.getAccidentID());
            exemptionDao.create(exemption);
            System.out.println("getJudgementComplete: " + selectedAccident.getJudgementComplete());
            accidentDao.updateJudged(selectedAccident); // judgementComplete를 1로 --> 면부책 완료, 손해사정만 남음
            break;
        }
    }

    private static void damageAssessment(BufferedReader objReader) throws NumberFormatException, IOException, MessagingException {
        AccidentList accidentList = new AccidentListImpl();
        RewardInfo rewardInfo = new RewardInfo();
        ExemptionDaoImpl exemptionDao = new ExemptionDaoImpl();
        DamageDaoImpl damageDao = new DamageDaoImpl();

        while (true) {
            ArrayList<Exemption> damageAssessmentList = exemptionDao.retrieveList().getExemptionList();
            for (Exemption sAccident : damageAssessmentList) {
                System.out.println("사고번호 : " + sAccident.getAccidentID());
                System.out.println("사고명 : " + sAccident.getAccidentTitle());
                System.out.println("사고발생날짜 : " + sAccident.getAccidentDate());
            }

            int select = Integer.parseInt(objReader.readLine());

            Exemption selectedExemption = exemptionDao.retrieveList().search(select);
            System.out.println("면/부책 여부: " + selectedExemption.getJudgementComplete() +
                    "\n판단 사유: " + selectedExemption.getReason() +
                    "\n참고자료: " + selectedExemption.getSubFile() +
                    "\n관련법규: " + selectedExemption.getLegacy());

            if (selectedExemption.getReason().equals("")) {
                rewardException.NoExemptionInfo(objReader);
                System.out.println("1. 확인");
                select = Integer.parseInt(objReader.readLine());
                if (select == 1) {
                    continue;
                }
            } else if (selectedExemption.getLegacy().equals("")) {
                rewardException.NoExemptionInfo(objReader);
                System.out.println("1. 확인");
                select = Integer.parseInt(objReader.readLine());
                if (select == 1) {
                    continue;
                }
            }

            System.out.println("지급액");
            String payment = objReader.readLine();
            if (payment.equals("")) {
                System.out.println("미입력정보가 존재합니다");
                continue;
            }
            rewardInfo.setPayment(payment);
            System.out.println("판단 사유");
            String assessReason = objReader.readLine();
            if (assessReason.equals("")) {
                System.out.println("미입력정보가 존재합니다");
                continue;
            }
            rewardInfo.setAssessReason(assessReason);

            System.out.println("손해사정을 등록하시겠습니까? 1. 네 2. 아니오");
            select = Integer.parseInt(objReader.readLine());
            if (select == 1) {
                rewardInfo.setAccident(selectedExemption);
                damageDao.create(rewardInfo);
                accidentList.delete(selectedExemption.getAccidentID());
                System.out.println("1. 보상");
                select = Integer.parseInt(objReader.readLine());
                if (select == 1) {
                    reward(objReader, rewardInfo.getAccident().getAccidentID());
                }
                break;
            } else if (select == 2) {
                System.out.println("손해사정을 취소하였습니다.");
                continue;
            }
        }
    }

    private static void reward(BufferedReader objReader, int accidentID) throws NumberFormatException, IOException, MessagingException {
        DamageDaoImpl damageDao = new DamageDaoImpl();
        String accidentType = damageDao.retrieveAccidentByID(accidentID);
        System.out.println("1. 입금");

        MailSender mailSender = new MailSender();
        String customerEMail = damageDao.retrieveCustomerEmail(accidentID);
        String title = "보상금이 입금되었습니다.";
        String content = "보상금이 입금되었습니다.";
        mailSender.sendEmail(customerEMail, title, content);

        int select = Integer.parseInt(objReader.readLine());
        if (select == 1) {
            if (accidentType.equals("Building")) {
                System.out.println("7일 이내에 실제 손해액(감가상각) 기준으로" +
                        " 주택 가재도구 복구 비용, 폐기물 운반 및 소각 비용에 대한 보험금을 지급 완료했습니다.");
            } else if (accidentType.equals("Driver")) {
                System.out.println("7일 이내에 대물보상-교환가액, 대체비용, 수리비, 대차료(비 사업용), 휴차료(사업용)" +
                        "/ 자기차량손해-사고발생 당시의 보험개발원이 정한 최근 차량기준가액, 가입금액 한도 내 차량 수리비에 대한 보험금을 지급 완료했습니다.");
            } else if (accidentType.equals("Car")) {
                System.out.println("3영업일 이내에 대인배상 I, II, 무보험차 상해-장례비, 위자료, 상실수익액, 위자료, 휴업손해, 치료관계비 " +
                        "/ 자기신체사고-가입금액에 따른 사망보험금, 가입금액에 따른 후유장애 또는 상해 등급별 한도금액 내 발생 보험금에 대한 보험금을 지급 완료했습니다.");
            }
        }
    }

}