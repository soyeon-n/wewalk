package com.spring.boot.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PaymentService {

	/*
	@Autowired
	private PaymentRepository paymentRepository;
	
	@Autowired
	private ActionBoardService actionBoardService;
	
	@Autowired
	private MemberService memberService;
	
	
	/**
	 * 은행이름에 따른 코드들을 반환해줌<br>
	 * KG이니시스 기준.
	 * @param bankName
	 * @return
	 */
	public String code(String bankName) {
		String code="";
		if(bankName.equals("우리은행")||bankName.equals("우리")) code="20";
		else if(bankName.equals("국민은행")||bankName.equals("국민")) code="04";
		return code;
	}
	
	/**
	 * 현재 결제번호에 해당하는 정보를 갖고와서 반환해줌.
	 * @param paymentsNo
	 * @return
	 */
	
	/*
	@Transactional
	public PaymentsInfo paymentLookupService(long paymentsNo) {
		PaymentsInfo paymentsInfo = paymentRepository.getById(paymentsNo);
		return paymentsInfo;
	}
	
	/**
	 * 아임포트 서버쪽 결제내역과 DB에 물건가격을 비교하는 서비스. <br>
	 * 다름 -> 예외 발생시키고 GlobalExceptionHandler쪽에서 예외처리 <br>
	 * 같음 -> 결제정보를 DB에 저장(PaymentsInfo 테이블)
	 * @param irsp (아임포트쪽 결제 내역 조회 정보)
	 * @param actionBoardNo (내 DB에서 물건가격 알기위한 경매게시글 번호)
	 * @throws verifyIamportException
	 */
	/*
	@Transactional
	public void verifyIamportService(IamportResponse<Payment> irsp, int amount, long actionBoardNo) throws verifyIamportException {
		ActionBoard actionBoard = actionBoardService.findPostByActionBoardNo(actionBoardNo);

		
		//실제로 결제된 금액과 아임포트 서버쪽 결제내역 금액과 같은지 확인
		//이때 가격은 BigDecimal이란 데이터 타입으로 주로 금융쪽에서 정확한 값표현을 위해씀.
		//int형으로 비교해주기 위해 형변환 필요.
		if(irsp.getResponse().getAmount().intValue()!=amount)
			throw new verifyIamportException();
				
		//DB에서 물건가격과 실제 결제금액이 일치하는지 확인하기. 만약 다르면 예외 발생시키기.
		if(amount!=actionBoard.getImmediatly()) 
			throw new verifyIamportException();
		
		//아임포트에서 서버쪽 결제내역과 DB의 결제 내역 금액이 같으면 DB에 결제 정보를 삽입.
		Member member = memberService.findByEmail(irsp.getResponse().getBuyerEmail());
		
		PaymentsInfo paymentsInfo = PaymentsInfo.builder()
				.payMethod(irsp.getResponse().getPayMethod())
				.impUid(irsp.getResponse().getImpUid())
				.merchantUid(irsp.getResponse().getMerchantUid())
				.amount(irsp.getResponse().getAmount().intValue())
				.buyerAddr(irsp.getResponse().getBuyerAddr())
				.buyerPostcode(irsp.getResponse().getBuyerPostcode())
				.member(member)
				.actionBoard(actionBoard)
				.build();
		
		paymentRepository.save(paymentsInfo);
	}
	
	/**
	 * 결제 취소할때 필요한 파라미터들을
	 * CancelData에 셋업해주고 반환함.
	 * @param map
	 * @param impUid
	 * @param bankAccount
	 * @param code
	 * @return
	 * @throws RefundAmountIsDifferent 
	 */
	/*
	@Transactional
	public CancelData cancelData(Map<String,String> map, 
			IamportResponse<Payment> lookUp,
			PrincipalDetail principal, String code) throws RefundAmountIsDifferent {
		//아임포트 서버에서 조회된 결제금액 != 환불(취소)될 금액 이면 예외발생
		if(lookUp.getResponse().getAmount()!=new BigDecimal(map.get("checksum"))) 
			throw new RefundAmountIsDifferent();
		
		CancelData data = new CancelData(lookUp.getResponse().getImpUid(),true);
		data.setReason(map.get("reason"));
		data.setChecksum(new BigDecimal(map.get("checksum")));
		data.setRefund_holder(map.get("refundHolder"));
		data.setRefund_bank(code);
		data.setRefund_account(principal.getBankName());
		return data;
	}
	*/
	
	
}
