<?php

require_once __DIR__ . '/../../vendor/swiftmailer/swiftmailer/lib/swift_required.php';

use Phalcon\Mvc\User\Component;
use Phalcon\Mvc\View;

/**
 * Send e-mails based on pre-defined templates.
 *
 * @package TestZilla\library\MailSender
 * @author Xie Haozhe <zjhzxhz@gmail.com>
 */
class MailSender extends Component {
    /**
     * Send email to a recipient.
     * @param  String $recipient    - the recipient of the email
     * @param  String $subject      - the subject of the email
     * @param  String $templateName - the file name of the template of the email
     * @param  Array  $parameters   - the parameters passed to the template
     * @return whether the mail is successfully sent
     */
    public function sendMail($recipient, $subject, $templateName, $parameters) {
        $mailSettings   = $this->config->mail;
        $mailContent    = $this->getMailContent($templateName, $parameters);
        
        $message        = Swift_Message::newInstance();
        $message->setSubject($subject)
                ->setTo($recipient)
                ->setFrom(array(
                    $mailSettings->senderMail => $mailSettings->senderName
                ))
                ->setBody($mailContent, 'text/html');

        if ( $this->transport == NULL ) {
            $this->transport = Swift_SmtpTransport::newInstance(
                $mailSettings->host,
                $mailSettings->port,
                $mailSettings->encryption
            );
            $this->transport->setUsername($mailSettings->username);
            $this->transport->setPassword($mailSettings->password);
        }

        $mailer = Swift_Mailer::newInstance($this->transport);
        return $mailer->send($message);
    }

    /**
     * Get the content of an email.
     * @param  String $templateName - the file name of the template of the email
     * @param  Array  $parameters   - the parameters passed to the template
     * @return the content of an email
     */
    private function getMailContent($templateName, $parameters) {
        $parameters     = array_merge(array(
            'baseUrl'   => $this->config->application->baseUrl,
        ), $parameters);

        return $this->view->getRender('mails', $templateName, $parameters, function($view){
            $view->setRenderLevel(View::LEVEL_LAYOUT);
        });
    }

    /**
     * The transport used to send email.
     * @var Swift_SmtpTransport
     */
    protected $transport;
}