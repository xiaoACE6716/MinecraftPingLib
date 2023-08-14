MinecraftPingLib
===================

一个很简单的 Minecraft Ping 库,用于获取目标服务器的状态与信息.  
直接返回一个 String 类型的 json 字符串(请使用者自行解析)  
为什么我不直接在这个库里做好解析?因为不同的人喜欢用不同的JSON库!  
推荐使用: Gson,HuTool-Json,FastJson等JSON库

## 如何引入此库
推荐使用 Maven 或者 Gradle 并从Jitpack获取  
https://jitpack.io/#xiaoACE6716/MinecraftPingLib
### Maven

```xml
=<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>

<dependency>
    <groupId>com.github.xiaoACE6716</groupId>
    <artifactId>MinecraftPingLib</artifactId>
    <version>Tag</version>
</dependency>
```

### Gradle
```groovy
allprojects {
		repositories {
			maven { url 'https://jitpack.io' }
		}
	}
	
dependencies {
	        implementation 'com.github.xiaoACE6716:MinecraftPingLib:Tag'
}	
```



## 返回的 json 格式
来自: [Wiki](https://wiki.vg/Server_List_Ping#Response)

```json
{
    "version": {
        "name": "1.19.4",
        "protocol": 762
    },
    "players": {
        "max": 100,
        "online": 5,
        "sample": [
            {
                "name": "thinkofdeath",
                "id": "4566e69f-c907-48ee-8d71-d7ba5aa00d20"
            }
        ]
    },
    "description": {
        "text": "Hello world"
    },
    "favicon": "data:image/png;base64,<data>",
    "enforcesSecureChat": true,
    "previewsChat": true
}
```
如果是 Forge 服务端，可能还会有 modinfo
```json
{
  ...内容同上
  "modinfo": {
    "type": "FML",
    "modList": [
      {
        "modid": "minecraft",
        "version": "1.12.2"
      },
      {
        "modid": "mcp",
        "version": "9.42"
      }
    ]
  }        
          
}
```

## 使用例子
```java
public class Example {
    public static void main(String[] args) {
        
        MCPingOptions options = MCPingOptions.builder()
                .hostname("mc.hypixel.net")
                .port(25565)
                .build();
        
        try {
            System.out.println(McPing.getPing(options));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        
    }
}
```

