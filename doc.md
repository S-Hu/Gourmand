# Gourmand文档
## 接口-Eater
- takeABite()
- isFull()

## 抽象类-Animal（实现Eater）
- stomach
- eatingSpeed
- amountPerBite
- cry()
- takeABite()
- isFull()

### 类-Stomach
- capacity
- nowEaten
- getEmpty()
- fill()

## 类-Dog（继承Animal）
- toEats(getter, setter)
- cry
- run

## 接口-Eatable
- getRemainingAmount()
- isClear
- beEaten

## 抽象类-Fruit
- remaining
- getRemainingAmount()
- isClear()
- beEaten()

## 类-西瓜（继承Fruit）



