# udsonk - UDS on Kotlin

[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)
![Project Status](https://img.shields.io/badge/status-pre--alpha-red)

Kotlin-native implementation of ISO 14229 (Unified Diagnostic Services) protocol for automotive systems.

## 项目状态

developing

## 功能规划

### 核心协议支持
| 功能                 | 状态 |
|--------------------|----|
| ISO 14229-1 协议框架   | ❌  |
| 基础报文构建器 (PCI, SID) | ❌  |
| 否定响应码(NRC)处理机制     | ❌  |
| 多帧报文处理（分段与重组）      | ❌  |

### UDS子服务实现
| 服务ID | 服务名称      | 状态 | 分类     |
|------|-----------|----|--------|
| 0x10 | 诊断会话控制    | ❌  | 会话管理类  |
| 0x11 | ECU复位     | ❌  | 设备控制类  |
| 0x14 | 清除诊断信息    | ❌  | DTC管理类 |
| 0x19 | 读取诊断故障码   | ❌  | DTC管理类 |
| 0x22 | 按标识符读数据   | ❌  | 数据访问类  |
| 0x23 | 按地址读内存    | ❌  | 内存访问类  |
| 0x24 | 按标识符读比例数据 | ❌  | 数据访问类  |
| 0x27 | 安全访问      | ❌  | 安全控制类  |
| 0x28 | 通信控制      | ❌  | 通信管理类  |
| 0x2E | 按标识符写数据   | ❌  | 数据访问类  |
| 0x2F | 输入输出控制    | ❌  | 设备控制类  |
| 0x31 | 例程控制      | ❌  | 程序控制类  |
| 0x34 | 请求下载      | ❌  | 数据传输类  |
| 0x35 | 请求上传      | ❌  | 数据传输类  |
| 0x36 | 传输数据      | ❌  | 数据传输类  |
| 0x37 | 请求传输退出    | ❌  | 数据传输类  |
| 0x3D | 按地址写内存    | ❌  | 内存访问类  |
| 0x3E | 待机握手      | ❌  | 会话管理类  |
| 0x85 | 控制DTC设置   | ❌  | DTC管理类 |
| 0x86 | 事件响应      | ❌  | 事件管理类  |
| 0x87 | 链路控制      | ❌  | 通信管理类  |

### 辅助功能
| 模块       | 状态 |
|----------|----|
| 报文解析调试工具 | ❌  |
| 异常处理框架   | ❌  |
| 日志追踪系统   | ❌  |
| 自动重试机制   | ❌  |
