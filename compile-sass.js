/**
 * Use node-sass to compile SASS files from SRC_DIR to OUTPUT_DIR.
 *
 * This script should _not_ be called directly, but invoked via one of the `npm run` commands
 * defined in the package.json.
 */

const fs = require('fs');
const path = require('path');
const sass = require('node-sass');

const SRC_DIR = path.resolve(__dirname, 'src/main/sass');
const OUTPUT_DIR = path.resolve(__dirname, 'src/main/resources/static');

const output = sass.renderSync({
    file: SRC_DIR + '/index.scss',
    // N.B. this does not write a file, it allows the source-map to refer to the generated file
    outFile: OUTPUT_DIR + '/index.css',
    outputStyle: 'compressed',
    sourceMap: true,
    sourceMapContents: true
});

fs.writeFileSync(OUTPUT_DIR + '/index.css', output.css);
fs.writeFileSync(OUTPUT_DIR + '/index.css.map', output.map);
