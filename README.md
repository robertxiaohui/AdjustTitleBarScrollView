# AdjustTitleBarScrollView
滑动调节titlebar的背景颜色,l类似QQ空间滑动透明titlebar和不透明的效果


使用时,请先调用setTitleBar,将titlebar传递进来,这样才能正常使用

需要修改titlebar颜色的时候,调用setTitleBarColor(String color)方法,否则默认为白色背景.设置时,传入的颜色值格式应该为:RRGGBB,并且不能带#
