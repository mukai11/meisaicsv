# meisaicsv

## 目的
銀行やクレジットカードの明細一覧CSV をフォーマットを統一させる。


## 対応機関一覧
こちら参照

https://docs.google.com/spreadsheets/d/1021phhLNDIy9s7eMswZIpa_rFGjDZUDvNZFGRp4-1HA


## 動作環境
java 8以上


## 使い方
引数1 : 対応機関のキー 。上記スプレッドシート参照。必須

引数2 : Csv ファイルパス。必須

引数3 : 出力形式、CSV or JSON。省略可能

 
<br/>

例

java -jar dist/meisaicsv-x.x.x-jar-with-dependencies.jar SbiNetBank /tmp/sbi.csv


