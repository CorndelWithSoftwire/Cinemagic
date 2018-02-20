const path = require('path');

const SRC_DIR = path.resolve(__dirname, 'src/main/javascript');
const OUTPUT_DIR = path.resolve(__dirname, 'src/main/resources/static');

module.exports = {
    entry: SRC_DIR + '/index.jsx',
    output: {
        path: OUTPUT_DIR,
        filename: 'index.js'
    },
    module: {
        loaders: [
            {
                test: /\.jsx?/,
                include: SRC_DIR,
                loader: 'babel-loader'
            }
        ]
    },
    resolve: {
        extensions: ['.js', '.jsx']
    },
    // 'source-map'
    // Generates a separate source map file, which can be used when debugging in production.
    //
    // 'eval-cheap-module-source-map'
    // Generates an inline source map with line numbers which is much faster to write, suitable for dev.
    devtool: process.env.NODE_ENV === 'production' ? 'source-map' : 'eval-cheap-module-source-map'
};
