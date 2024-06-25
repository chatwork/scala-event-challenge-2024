# 【Scala経験者限定！】アクタープログラミングを使ってビジネスチャットの実装に挑戦しよう: 事前課題

## 課題の概要

これはFizzBuzzを少し改変した課題です。課題の解答のSHA-256ハッシュ値をイベントの申し込みフォームにご入力ください。

申し込みフォーム：

https://forms.gle/c8oKxQSDwuKmqwce6

イベント詳細：

https://chatwork.connpass.com/event/320869/

## 課題の詳細

1から100までの数を順に評価し、以下のルールに従って各数値に対応する文字列を生成してください。

- 数値が3で割り切れる場合は「Fizz」
- 数値が5で割り切れる場合は「Buzz」
- 数値が7で割り切れる場合は「Jazz」
- 数値が3と5の両方で割り切れる場合は「FizzBuzz」
- 数値が3と7の両方で割り切れる場合は「FizzJazz」
- 数値が5と7の両方で割り切れる場合は「BuzzJazz」
- 数値が3と5と7の全てで割り切れる場合は「FizzBuzzJazz」
- 上記のいずれにも該当しない場合は、その数値を文字列としてそのまま出力します。

ひな型のコードは、当該ディレクトリにあるsbtプロジェクトの `src/main/scala/main.scala` を参照してください。
上記によって生成された文字列型のコレクション(`Seq[String]`)を `computeSha256Hex` 関数の引数に与えて得られる結果のハッシュ値を提出してください。

- FizzBuzzでは標準出力に答えを出力しますが、今回は文字列型コレクション(`Seq[String]`)を生成する必要がありますので、注意してください。
- 正確なハッシュ値の提出がイベント参加のための必須条件です。ハッシュ値の計算間違いや、ルールの誤解があると参加資格が得られませんのでご注意ください。

当該sbtプロジェクトの [src/main/scala/main.scala](https://github.com/chatwork/scala-event-challenge-2024/blob/main/implement-chat-uising-actor-model/src/main/scala/main.scala#L5) に上記にのルールに沿うロジックを記述してください。

## 問い合わせ

課題内容について質問があれば、以下のissueから起票をお願いします。ベストエフォートで回答します。

https://github.com/chatwork/scala-event-challenge-2024/issues
