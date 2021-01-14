package com.epam.chat.commands;


public enum CommandTypes {
    KICK {
        public Command getCommand() {
            return new KickCommand();
        }
    },
    LOGIN {
        public Command getCommand() {
            return new LoginCommand();
        }
    },
    LOGOUT {
        public Command getCommand() {
            return new LogoutCommand();
        }
    },
    MESSAGE {
        public Command getCommand() {
            return new MessageCommand();
        }
    },
    UNKICK {
        public Command getCommand() {
            return new UnKickCommand();
        }
    };
    public abstract Command getCommand();
}
