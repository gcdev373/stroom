package stroom.refdata.offheapstore;

import net.sf.saxon.event.PipelineConfiguration;
import net.sf.saxon.event.Receiver;

import java.nio.ByteBuffer;

public abstract class AbstractByteBufferConsumer implements RefDataValueByteBufferConsumer {
    private final Receiver receiver;

    AbstractByteBufferConsumer(final Receiver receiver) {
        this.receiver = receiver;
    }

    Receiver getReceiver() {
        return receiver;
    }

    @Override
    public abstract void consumeBytes(Receiver receiver, ByteBuffer byteBuffer);



    public interface ByteBufferConsumerFactory {
        RefDataValueByteBufferConsumer create(final Receiver receiver, final PipelineConfiguration pipelineConfiguration);
    }
}
