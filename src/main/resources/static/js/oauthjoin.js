
// 화살표 함수
const sendIt = () => {
	
    let f = document.myForm;

    let fields = [
        { field: f.name, message: "이름을 입력하세요." },
        { field: f.postcode, message: "우편번호를 입력하세요." },
        { field: f.address, message: "주소를 입력하세요." },
        { field: f.detailAddress, message: "상세 주소를 입력하세요." },
        { field: f.tel, message: "전화번호를 입력하세요." },
        { field: f.birthYear, message: "생년을 입력하세요." },
        { field: f.birthMonth, message: "생월을 입력하세요." },
        { field: f.birthDay, message: "생일을 입력하세요." }
    ];

    for (let i = 0; i < fields.length; i++) {
        let str = fields[i].field.value.trim();
        if (!str) {
            alert(`\n${fields[i].message}`);
            fields[i].field.focus();
            return false;
        }
        fields[i].field.value = str;
    }

    return true;
}
