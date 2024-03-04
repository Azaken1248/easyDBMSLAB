import pyautogui
import pygetwindow as gw

import time

def find_whatsapp_window():
    whatsapp_window = gw.getWindowsWithTitle('WhatsApp')[0]
    return whatsapp_window

def type_message_in_whatsapp(message):
    whatsapp_window = find_whatsapp_window()
    whatsapp_window.activate()

    time.sleep(2)

    pyautogui.typewrite(message)
    pyautogui.press('enter')

if __name__ == "__main__":
    message_to_send = "Hello, this is a test message."

    type_message_in_whatsapp(message_to_send)
