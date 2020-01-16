<?php
/**
 * @Author: Haozhe Xie
 * @Date:   2020-01-16 12:31:04
 * @Last Modified by:   Haozhe Xie
 * @Last Modified time: 2020-01-16 13:07:01
 */

use Phalcon\Mvc\Model;

/**
 * The options of the application.
 *
 * @package TestZilla\model\Option
 */
class Option extends Model {
    /**
     * Initialize this model.
     */
    public function initialize() {
        $this->setSource(self::TABLE_NAME);
    }

    /**
     * The getter of the field option_id.
     * @return the unique ID of the option
     */
    public function getOptionId() {
        return $this->option_id;
    }

    /**
     * The setter of the field option_id;
     * @param long $optionId - the unique ID of the option
     */
    public function setOptionId($optionId) {
        $this->option_id = $optionId;
    }

    /**
     * The getter of the field option_key.
     * @return the unique name of the option
     */
    public function getOptionKey() {
        return $this->option_key;
    }

    /**
     * The setter of the field option_key.
     * @param String $optionKey - the unique name of the option
     */
    public function setOptionKey($optionKey) {
        if ( mb_strlen($optionKey, 'utf-8') > 64 ) {
            throw new InvalidArgumentException('[Model\Option] The length of optionKey CANNOT exceed 64 characters.');
        }
        $this->option_key = $optionKey;
    }

    /**
     * The getter of the field option_value.
     * @return the value of the option
     */
    public function getOptionValue() {
        return $this->option_value;
    }

    /**
     * The setter of the field option_value.
     * @param String $optionValue - the value of the option
     */
    public function setOptionValue($optionValue) {
        $this->option_value = $optionValue;
    }

    /**
     * Whether the option is loaded all the time.
     * @return whether the option is loaded all the time 
     */
    public function isAutoload() {
        return $is_autoload;
    }

    /**
     * Get the description of the Option object.
     * @return the description of the Option object
     */
    public function __toString() {
        return sprintf('Option: [ID=%s, Key=%s, Value=%s]', 
                $this->option_id, $this->option_key, $this->option_value);
    }
    
    /**
     * The unique ID of the option.
     * @var long
     */
    protected $option_id;

    /**
     * The unique name of the option.
     * @var String
     */
    protected $option_key;

    /**
     * The value of the option.
     * @var String
     */
    protected $option_value;

    /**
     * Whether the option is loaded all the time.
     * @var boolean
     */
    protected $is_autoload;

    /**
     * The table name of the model.
     */
    const TABLE_NAME = 'tz_options';
}
