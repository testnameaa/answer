select
  jyuno,
  tori_cd,
  tori_nm,
  bra_cd,
  bra_nm,
  hat_date,
  syu_date,
  nou_date,
  den_no,
  tok_cd,
  tok_nm,
  ukeba_cd,
  sok_cd,
  sok_nm,
  haiso_cd,
  haiso_nm,
  den_gyo,
  rhin_cd,
  hin_cd,
  hin_nm,
  barasu,
  gen_tan,
  gen_kin,
  bai_tan,
  bai_kin,
  out_flg,
  ins_at,
  upd_at
from
  jyu_org
where
/* 練習問題13 回答例（※ここは回答例をコメントにしていません） */
  bra_cd = /* braCd */'00000000' and
  syu_date between /* startDate */'2020-01-01' and /* endDate */'2020-12-31'

