import { createLogger, format, transports } from 'winston';

const { combine, timestamp, printf, colorize } = format;

// Custom Log format
const logFormat = printf(({ level, message, timestamp }) => {
    return `${timestamp} [${level}]: ${message}`;
});

// Create the Logger instance
const logger = createLogger({
    level: 'info',
    format: combine(
        timestamp({ format: 'YYYY-MM-DD HH:mm:ss' }),
        colorize({ all: true, colors: { info: 'green', error: 'red', warn: 'yellow' } }),
        logFormat
    ),
    transports: [
        new transports.Console({
            format: combine(
                timestamp({ format: 'YYYY-MM-DD HH:mm:ss' }),
                colorize(),
                logFormat
            )
        }),
        new transports.File({
            filename: 'logs/error.log',
            level: 'error',
            format: combine(
                timestamp({ format: 'YYYY-MM-DD HH:mm:ss' }),
                logFormat
            )
        }),
        new transports.File({
            filename: 'logs/combined.log',
            format: combine(
                timestamp({ format: 'YYYY-MM-DD HH:mm:ss' }),
                logFormat
            )
        })
    ]
});

export default logger;
