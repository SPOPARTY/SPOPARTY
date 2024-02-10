function formatDateTime(dateTimeStr) {
    // Date 객체 생성
    const date = new Date(dateTimeStr);

    // 연도, 월, 일, 시, 분을 추출
    const year = date.getFullYear();
    const month = (date.getMonth() + 1).toString().padStart(2, '0'); // getMonth는 0부터 시작
    const day = date.getDate().toString().padStart(2, '0');
    const hour = date.getHours().toString().padStart(2, '0');
    const minute = date.getMinutes().toString().padStart(2, '0');

    // 포맷에 맞게 반환
    return `${year}-${month}-${day} ${hour}:${minute}`;
}

export{
    formatDateTime
} 
