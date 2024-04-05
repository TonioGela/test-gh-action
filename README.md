# test-gh-action

Example of a super simple Github Action written in [Scala.js](https://github.com/scala-js/scala-js) using [scala-cli](https://github.com/VirtusLab/scala-cli) and the [Typelevel Toolkit](https://github.com/typelevel/toolkit) as described in [this blog post](https://toniogela.dev/gh-action-in-scala/).

```yaml
- name: Sum numbers with Scala
  id: this-is-the-id
  uses: TonioGela/test-gh-action@main
  with:
      number-one: 3
      number-two: 9
- run: test 12 -eq "${{ steps.this-is-the-id.outputs.result }}"
```
