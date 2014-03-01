PullScrollView
===========

*注：本项目使用Android Studio开发*


## **本例包含ScrollView的两种实现：** ##
>1.仿照新浪微博Android客户端个人中心的ScrollView，下拉背景伸缩回弹效果。<br>
>2.ScrollView仿IOS回弹效果。<br>

## **使用示例** ##

```java
mScrollView = (PullScrollView) findViewById(R.id.scroll_view);
mHeadImg = (ImageView) findViewById(R.id.background_img);
mScrollView.setOnTurnListener(this);
mScrollView.init(mHeadImg);
```

##Screenshots
![Screenshot 0](https://raw.github.com/MarkMjw/PullScrollView/master/Screenshots/0.png)

![Screenshot 1](https://raw.github.com/MarkMjw/PullScrollView/master/Screenshots/1.png)

![Screenshot 2](https://raw.github.com/MarkMjw/PullScrollView/master/Screenshots/2.png)

License
=======

    Copyright (C) 2014 MarkMjw

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
