package Manager;

import Panel.MessagePanel;

public class MessageManager
{
    public static MessageManager Instance = new MessageManager();
    private MessagePanel messagePanel;

    public void setMessagePanel(MessagePanel messagePanel)
    {
        this.messagePanel = messagePanel;
    }

    private MessageManager()
    {

    }

    public void PrintMessage(String content)
    {
        messagePanel.PrintMessage(content);
    }
}
