select
  tori_cd,
  rhin_cd_org,
  rhin_cd_dst
from
  rhin_conv_mst
where
  tori_cd = /* toriCd */'a'
  and
  rhin_cd_org = /* rhinCdOrg */'a'
