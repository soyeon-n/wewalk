package com.spring.boot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PaymentsApiController {

	/*
private final IamportClient iamportClientApi;
	
	//생성자로 rest api key와 secret을 입력해서 토큰 바로생성.
	public PaymentsApiController() {
		this.iamportClientApi = new IamportClient("2620325036077017",
				"4MrBhqYpkWn8HMtBA6qndOCCJprF4swb8s5f05bzlmof73p7w1fAEfeLl64klF0bttj2aPtFxuKNfFaR");
	}
	
	@Autowired
	private PaymentService paymentService;
	
	/**
	 * impUid로 결제내역 조회.
	 * @param impUid
	 * @return
	 * @throws IamportResponseException
	 * @throws IOException
	 */
	/*
	public IamportResponse<Payment> paymentLookup(String impUid) throws IamportResponseException, IOException{
		return iamportClientApi.paymentByImpUid(impUid);
	}
	/*
	/**
	 * impUid를 결제 번호로 찾고, 조회해야하는 경우.<br>
	 * 오버로딩.
	 * @param paymentsNo
	 * @return
	 * @throws IamportResponseException
	 * @throws IOException
	 *//*
	public IamportResponse<Payment> paymentLookup(long paymentsNo) throws IamportResponseException, IOException{
		PaymentsInfo paymentsInfo = paymentService.paymentLookupService(paymentsNo);
		return iamportClientApi.paymentByImpUid(paymentsInfo.getImpUid());
	}
	
	/**
	 * 결제검증을 위한 메서드<br>
	 * map에는 imp_uid, amount, actionBoardNo 이 키값으로 넘어옴.
	 * @param map
	 * @return
	 * @throws IamportResponseException
	 * @throws IOException
	 * @throws verifyIamportException
	 */
	/*
	@PostMapping("verifyIamport")
	public IamportResponse<Payment> verifyIamport(@RequestBody Map<String,String> map) throws IamportResponseException, IOException, verifyIamportException{
		String impUid = map.get("imp_uid");//실제 결제금액 조회위한 아임포트 서버쪽에서 id
		long actionBoardNo = Long.parseLong(map.get("actionBoardNo")); //DB에서 물건 가격 조회를 위한 번호
		int amount = Integer.parseInt(map.get("amount"));//실제로 유저가 결제한 금액
		
		//아임포트 서버쪽에 결제된 정보 조회.
		//paymentByImpUid 는 아임포트에 제공해주는 api인 결제내역 조회(/payments/{imp_uid})의 역할을 함. 
		IamportResponse<Payment> irsp = paymentLookup(impUid);

		paymentService.verifyIamportService(irsp, amount, actionBoardNo);
		
		return irsp;
	}
	*/
	
}
