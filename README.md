# Just Do it

## Requirements

One VM with debian 12, docker installed, SSH access, git installed.
4vCPUs 16 GB RAM advised. 2 vCPUs 8GB RAM minmal requirement.

## Just Do it

### Prepare for ansible

* Git clone it:

```bash
export OPS_HOME="/opt/.apromtale.ops/"

initOpsHome () {
    if [ -d ${OPS_HOME} ]; then
      # -
      echo "WARNING: the [${OPS_HOME}] folder already exist"
    fi;
}
      
sudo mkdir -p ${OPS_HOME}
sudo chown $USER: -R ${OPS_HOME}

export DESIRED_VERSION='feature/prom/tomcat/real/case'
git clone git@github.com:3forges/a-prom-tale.git ${OPS_HOME}

cd ${OPS_HOME}

git checkout ${DESIRED_VERSION}



```

* Then run it:

```bash

export AWXEE_IMG_TAG='24.6.1'
export AWXEE_IMG_GUN="quay.io/ansible/awx-ee:${AWXEE_IMG_TAG}"
export RUNNER_NAME="ansible_lab"
export OPS_HOME="/opt/.apromtale.ops/"



docker pull "${AWXEE_IMG_GUN}"

cd ${OPS_HOME}

docker run --name ${RUNNER_NAME} -itd --restart unless-stopped -v $PWD:/runner/src:rw ${AWXEE_IMG_GUN} bash

docker exec -w /runner/src -it ${RUNNER_NAME} bash -c 'ansible --version'

docker exec -w /runner/src -it ${RUNNER_NAME} bash -c 'ansible-playbook --version'

docker exec -w /runner/src -it ${RUNNER_NAME} bash -c 'ansible-inventory --version'

docker exec -w /runner/src -it ${RUNNER_NAME} bash -c 'ansible-vault --version'

docker exec -w /runner/src -it ${RUNNER_NAME} bash -c 'ansible-vault --version'


# ---
# execute the playbook


```
