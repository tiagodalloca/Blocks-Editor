FOR /F "tokens=5 delims= " %%P IN ('netstat -a -n -o ^| findstr :3449') DO TaskKill.exe /PID %%P /F
