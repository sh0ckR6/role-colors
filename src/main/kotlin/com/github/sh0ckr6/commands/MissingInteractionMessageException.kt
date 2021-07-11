package com.github.sh0ckr6.commands

class MissingInteractionMessageException : Exception("This command is missing an interaction reply Message! (Maybe you forgot to set replyMessage when replying?)")