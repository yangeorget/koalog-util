<%php
/*
 *
 * This file contains a set of reporting functions from an
 * RTF template.
 *
 */

/**
 * A low-level function that processes a single line of text.
 * @param line The line to process.
 * @param tags The key-value map for the tags.
 * @return The processed line.
 */
function processLine($line, $tags) {
    foreach ($tags as $key => $value) {
        $key = "\\{".$key."\\}";
        $line = str_replace($key, $value, $line);
    }
    return $line;
}



/**
 * The high-level report generation function.
 * @param report The report filename.
 * @param template The template filename.
 * @param tags The key-value map for the tags.
 * @return TRUE in case of success, false otherwise.
 */
function generateReport($report, $template, $tags) {
    // Check that the template exists
    if (!file_exists($template)) {
        return FALSE;
    }
    // get the template pointer
    $template_p = fopen($template, "r");
    if (!$template_p) {
        return FALSE;
    }
    // get the report pointer
    $report_p = fopen($report, "wb"); // b required for fwrite
    if (!$report_p) {
        return FALSE;
    }
    // process each line
    $eol_char = "\n"; // check the differences on various systems,
    // but supposedly, win ends the line with \r\n, and thus with \n,
    // which is ok.
    while (!feof($template_p)) {
        // get the next template line
        $line = fgets($template_p);
        while ($line{strlen($line) - 1} != $eol_char && !feof($template_p)) {
            $line = $line.fgets($template_p);
        }
        if (get_magic_quotes_runtime()) {
            $line = stripslashes($line);
        }
        // replace the tags by their values
        $line = processLine($line, $tags);
        // write the line to the report
        fwrite($report_p, $line);
    }
    // close the files
    fclose($template_p);
    fclose($report_p);
    return TRUE;
}

%>
