CREATE TABLE `employee` (
  `employee_id` bigint AUTO_INCREMENT PRIMARY KEY COMMENT '직원 ID',
  `name` varchar(100) COMMENT '직원 이름',
  `vacation_yn` varchar(1) COMMENT '휴가 여부',
  `use_yn` varchar(1) COMMENT '사용 여부',
  `regist_date_time` timestamp COMMENT '등록 일시',
  `modify_date_time` timestamp COMMENT '변경 일시'
);

CREATE TABLE `office_seat` (
  `office_seat_id` bigint AUTO_INCREMENT PRIMARY KEY COMMENT '사무실 좌석 ID',
  `office_seat_number` varchar(50) COMMENT '사무실 좌석 번호',
  `office_seat_location` varchar(1000) COMMENT '사무실 좌석 위치',
  `use_yn` varchar(1) COMMENT '사용 여부',
  `regist_date_time` timestamp COMMENT '등록 일시',
  `modify_date_time` timestamp COMMENT '변경 일시'
);

CREATE TABLE `emp_ofc_use` (
  `employee_id` bigint COMMENT '직원 ID',
  `office_seat_id` bigint COMMENT '사무실 좌석 ID',
  `reserve_seat_date` date COMMENT '좌석 예약 날짜',
  `use_yn` varchar(1) COMMENT '사용 여부',
  `regist_date_time` timestamp COMMENT '등록 일시',
  `modify_date_time` timestamp COMMENT '변경 일시',
  PRIMARY KEY (`office_seat_id`, `reserve_seat_date`)
);

ALTER TABLE `employee` COMMENT = '직원 테이블';

ALTER TABLE `office_seat` COMMENT = '사무실 좌석 테이블';

ALTER TABLE `emp_ofc_use` COMMENT = '직원, 사무실 좌석 맵핑 테이블';
